package ru.touchin.roboswag.core.log_file

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.touchin.roboswag.core.log.BuildConfig
import ru.touchin.roboswag.core.log.R
import ru.touchin.roboswag.core.log.databinding.DialogFragmentDebugLogsBinding
import ru.touchin.roboswag.core.log_file.LogFileManager
import java.io.File

class DebugLogsDialogFragment : DialogFragment() {

    private val logItemsList: MutableList<String> = mutableListOf()
    private lateinit var binding: DialogFragmentDebugLogsBinding

    override fun getTheme(): Int = R.style.DialogFullscreenTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogFragmentDebugLogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()

        binding.logsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.logsRecycler.adapter = LogItemAdapter(requireContext(), logItemsList)
        updateRecycler()

        binding.shareBtn.setOnClickListener {
            val files = LogFileManager(requireContext()).getLogDirectory().listFiles()

            files?.firstOrNull()?.let { firstFile ->
                val uri = FileProvider.getUriForFile(
                    requireContext(),
                    BuildConfig.LIBRARY_PACKAGE_NAME + LogFileManager.fileProviderName,
                    firstFile
                )

                val intent = Intent(Intent.ACTION_SEND)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setType("*/*")
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
        }
    }

    private fun initSpinner() {
        val priorityTitle = LogFileManager.Priority.values().map { it.title }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityTitle)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        binding.priorityFilter.adapter = adapter;
        binding.priorityFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val priority = LogFileManager.Priority.values()[position]
                LogFileManager(requireContext()).saveLogcatToFile(priority.tag)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        binding.updateBtn.setOnClickListener { updateRecycler() }
    }

    private fun updateRecycler() {
        logItemsList.clear()
        val files = LogFileManager(requireContext()).getLogDirectory().listFiles()
        files?.firstOrNull()?.let { firstFile ->
            File(firstFile.getAbsolutePath())
                .useLines { lines -> lines.forEach { logItemsList.add(it) } }
        }
        binding.logsRecycler.adapter?.notifyDataSetChanged()
    }
}
