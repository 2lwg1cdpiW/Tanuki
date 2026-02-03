package org.tanukis.tanuki.core.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import org.tanukis.tanuki.R
import org.tanukis.tanuki.core.github.AppUpdateRepository
import org.tanukis.tanuki.core.nav.AppRouter
import org.tanukis.tanuki.core.nav.router
import org.tanukis.tanuki.core.ui.AlertDialogFragment
import org.tanukis.tanuki.core.util.ext.copyToClipboard
import org.tanukis.tanuki.core.util.ext.getCauseUrl
import org.tanukis.tanuki.core.util.ext.isHttpUrl
import org.tanukis.tanuki.core.util.ext.isReportable
import org.tanukis.tanuki.core.util.ext.report
import org.tanukis.tanuki.core.util.ext.requireSerializable
import org.tanukis.tanuki.core.util.ext.setTextAndVisible
import org.tanukis.tanuki.databinding.DialogErrorDetailsBinding
import javax.inject.Inject

@AndroidEntryPoint
class ErrorDetailsDialog : AlertDialogFragment<DialogErrorDetailsBinding>(), View.OnClickListener {

	private lateinit var exception: Throwable

	@Inject
	lateinit var appUpdateRepository: AppUpdateRepository

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val args = requireArguments()
		exception = args.requireSerializable(AppRouter.KEY_ERROR)
	}

	override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogErrorDetailsBinding {
		return DialogErrorDetailsBinding.inflate(inflater, container, false)
	}

	override fun onViewBindingCreated(binding: DialogErrorDetailsBinding, savedInstanceState: Bundle?) {
		super.onViewBindingCreated(binding, savedInstanceState)
		binding.buttonBrowser.setOnClickListener(this)
		binding.textViewSummary.text = exception.message
		val isUrlAvailable = exception.getCauseUrl()?.isHttpUrl() == true
		binding.buttonBrowser.isVisible = isUrlAvailable
		binding.textViewBrowser.isVisible = isUrlAvailable
		binding.textViewDescription.setTextAndVisible(
			if (appUpdateRepository.isUpdateAvailable) {
				R.string.error_disclaimer_app_outdated
			} else if (exception.isReportable()) {
				R.string.error_disclaimer_report
			} else {
				0
			},
		)
	}

	@Suppress("NAME_SHADOWING")
	override fun onBuildDialog(builder: MaterialAlertDialogBuilder): MaterialAlertDialogBuilder {
		val builder = super.onBuildDialog(builder)
			.setCancelable(true)
			.setNegativeButton(R.string.close, null)
			.setTitle(R.string.error_details)
			.setNeutralButton(androidx.preference.R.string.copy) { _, _ ->
				context?.copyToClipboard(getString(R.string.error), exception.stackTraceToString())
			}
		if (appUpdateRepository.isUpdateAvailable) {
			builder.setPositiveButton(R.string.update) { _, _ ->
				router.openAppUpdate()
				dismiss()
			}
		} else if (exception.isReportable()) {
			builder.setPositiveButton(R.string.report) { _, _ ->
				exception.report(silent = true)
				dismiss()
			}
		}
		return builder
	}

	override fun onClick(v: View) {
		router.openBrowser(
			url = exception.getCauseUrl() ?: return,
			source = null,
			title = null,
		)
	}
}
