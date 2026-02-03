package org.tanukis.tanuki.details.ui

import com.google.android.material.snackbar.Snackbar
import org.tanukis.tanuki.R
import org.tanukis.tanuki.core.exceptions.UnsupportedSourceException
import org.tanukis.tanuki.core.exceptions.resolve.ErrorObserver
import org.tanukis.tanuki.core.exceptions.resolve.ExceptionResolver
import org.tanukis.tanuki.core.util.ext.getDisplayMessage
import org.tanukis.tanuki.core.util.ext.isNetworkError
import org.tanukis.tanuki.core.util.ext.isSerializable
import org.tanukis.tanuki.parsers.exception.NotFoundException
import org.tanukis.tanuki.parsers.exception.ParseException

class DetailsErrorObserver(
	override val activity: DetailsActivity,
	private val viewModel: DetailsViewModel,
	resolver: ExceptionResolver?,
) : ErrorObserver(
	activity.viewBinding.scrollView, null, resolver,
	{ isResolved ->
		if (isResolved) {
			viewModel.reload()
		}
	},
) {

	override suspend fun emit(value: Throwable) {
		val snackbar = Snackbar.make(host, value.getDisplayMessage(host.context.resources), Snackbar.LENGTH_SHORT)
		snackbar.setAnchorView(activity.viewBinding.containerBottomSheet)
		if (value is NotFoundException || value is UnsupportedSourceException) {
			snackbar.duration = Snackbar.LENGTH_INDEFINITE
		}
		when {
			canResolve(value) -> {
				snackbar.setAction(ExceptionResolver.getResolveStringId(value)) {
					resolve(value)
				}
			}

			value is ParseException -> {
				val router = router()
				if (router != null && value.isSerializable()) {
					snackbar.setAction(R.string.details) {
						router.showErrorDialog(value)
					}
				}
			}

			value.isNetworkError() -> {
				snackbar.setAction(R.string.try_again) {
					viewModel.reload()
				}
			}
		}
		snackbar.show()
	}
}
