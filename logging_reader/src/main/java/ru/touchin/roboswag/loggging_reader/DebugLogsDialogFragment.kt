package ru.touchin.roboswag.loggging_reader

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import ru.touchin.roboswag.logging_reader.BuildConfig
import ru.touchin.roboswag.logging_reader.R
import ru.touchin.roboswag.logging_reader.databinding.DialogFragmentDebugLogsBinding
import ru.touchin.roboswag.navigation_base.fragments.viewBinding
import java.io.File

class DebugLogsDialogFragment : DialogFragment() {

    private val logItemsList: MutableList<String> = mutableListOf()
    private val binding: DialogFragmentDebugLogsBinding by viewBinding(DialogFragmentDebugLogsBinding::bind)

    override fun getTheme(): Int = R.style.DialogFullscreenTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.dialog_fragment_debug_logs, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()

        binding.logsRecycler.adapter = LogItemAdapter(requireContext(), logItemsList)
        updateRecycler()

        binding.updateBtn.setOnClickListener { updateRecycler() }
        binding.shareBtn.setOnClickListener { onShareButtonClick() }
    }

    private fun initSpinner() {
        val priorityTitle = LogFileManager.Priority.values().map { it.title }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityTitle)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        binding.priorityFilter.adapter = adapter
        binding.priorityFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val priority = LogFileManager.Priority.values()[position]
                LogFileManager(requireContext()).saveLogcatToFile(priority.tag)
                binding.logsRecycler.postDelayed({ updateRecycler() }, 500)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateRecycler() {
        binding.logsRecycler.adapter?.notifyItemRangeRemoved(0, logItemsList.size)
        logItemsList.clear()
        val files = LogFileManager(requireContext()).getLogDirectory().listFiles()
        files?.firstOrNull()?.let { firstFile ->
            File(firstFile.absolutePath)
                    .useLines { lines -> lines.forEach { logItemsList.add(it) } }
        }
        binding.logsRecycler.adapter?.notifyItemRangeInserted(0, logItemsList.size)
    }

    private fun onShareButtonClick() {
        val files = LogFileManager(requireContext()).getLogDirectory().listFiles()

        files?.firstOrNull()?.let { firstFile ->
            val uri = FileProvider.getUriForFile(
                    requireContext(),
                    BuildConfig.LIBRARY_PACKAGE_NAME + LogFileManager.fileProviderName,
                    firstFile
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                type = "*/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }

    }
}
