package ru.touchin.roboswag

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.math.BigInteger
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.util.Calendar
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal

// https://proandroiddev.com/secure-data-in-android-encryption-in-android-part-2-991a89e55a23
@Suppress("detekt.TooGenericExceptionCaught")
class PrefsCryptoUtils constructor(val context: Context) {

    companion object {

        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        private const val KEY_ALGORITHM_RSA = "RSA"
        private const val TRANSFORMATION_ASYMMETRIC = "RSA/ECB/PKCS1Padding"
        private const val CIPHER_STRING_SIZE_BYTES = 256
        private const val BASE_64_PADDING = 2
        private const val STORAGE_KEY = "STORAGE_KEY"

        //https://stackoverflow.com/questions/13378815/base64-length-calculation
        private const val ORIGINALLY_BYTES_COUNT = 3
        private const val ENCRYPT_BYTES_COUNT = 4
        private const val BASE64_DIVIDER_COUNT = 5
        const val ENCRYPT_BASE64_STRING_LENGTH =
                (CIPHER_STRING_SIZE_BYTES + BASE_64_PADDING) * ENCRYPT_BYTES_COUNT / ORIGINALLY_BYTES_COUNT + BASE64_DIVIDER_COUNT
        const val ENCRYPT_BLOCK_SIZE = 128

        private fun getAndroidKeystore(): KeyStore? = try {
            KeyStore.getInstance(ANDROID_KEY_STORE).also { it.load(null) }
        } catch (exception: Exception) {
            null
        }

        private fun getAndroidKeyStoreAsymmetricKeyPair(): KeyPair? {
            val privateKey = getAndroidKeystore()?.getKey(STORAGE_KEY, null) as PrivateKey?
            val publicKey = getAndroidKeystore()?.getCertificate(STORAGE_KEY)?.publicKey
            return if (privateKey != null && publicKey != null) {
                KeyPair(publicKey, privateKey)
            } else {
                null
            }
        }

        private fun createAndroidKeyStoreAsymmetricKey(context: Context): KeyPair {
            val generator = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA, ANDROID_KEY_STORE)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                initGeneratorWithKeyPairGeneratorSpec(generator)
            } else {
                initGeneratorWithKeyGenParameterSpec(generator, context)
            }

            // Generates Key with given spec and saves it to the KeyStore
            return generator.generateKeyPair()
        }

        private fun initGeneratorWithKeyGenParameterSpec(generator: KeyPairGenerator, context: Context) {
            val startDate = Calendar.getInstance()
            val endDate = Calendar.getInstance()
            endDate.add(Calendar.YEAR, 20)

            val builder = KeyPairGeneratorSpec.Builder(context)
                    .setAlias(STORAGE_KEY)
                    .setSerialNumber(BigInteger.ONE)
                    .setSubject(X500Principal("CN=$STORAGE_KEY CA Certificate"))
                    .setStartDate(startDate.time)
                    .setEndDate(endDate.time)

            generator.initialize(builder.build())
        }

        @TargetApi(Build.VERSION_CODES.M)
        private fun initGeneratorWithKeyPairGeneratorSpec(generator: KeyPairGenerator) {
            val builder = KeyGenParameterSpec.Builder(STORAGE_KEY, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            generator.initialize(builder.build())
        }

        private fun createCipher(): Cipher? = try {
            Cipher.getInstance(TRANSFORMATION_ASYMMETRIC)
        } catch (exception: Exception) {
            null
        }

    }

    private val cipher = createCipher()
    private val keyPair = getAndroidKeyStoreAsymmetricKeyPair()
            ?: createAndroidKeyStoreAsymmetricKey(context)

    // Those methods should not take and return strings, only char[] and those arrays should be cleared right after usage
    // See for explanation https://docs.oracle.com/javase/6/docs/technotes/guides/security/crypto/CryptoSpec.html#PBEEx

    @Synchronized
    fun encrypt(data: String): String {
        cipher?.init(Cipher.ENCRYPT_MODE, keyPair.public)
        val bytes = cipher?.doFinal(data.toByteArray())
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    @Synchronized
    fun decrypt(data: String?): String {
        cipher?.init(Cipher.DECRYPT_MODE, keyPair.private)
        if (data.isNullOrBlank()) {
            return String()
        }
        val encryptedData = Base64.decode(data, Base64.DEFAULT)
        val decodedData = cipher?.doFinal(encryptedData)
        return decodedData?.let { decodedData -> String(decodedData) } ?: ""
    }

}
