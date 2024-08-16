package com.danielnascimento.bancodigital.util

import androidx.fragment.app.Fragment
import com.danielnascimento.bancodigital.R
import com.danielnascimento.bancodigital.databinding.LayoutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.showBottomSheet(
    title: Int? = null,
    textButton: Int? = null,
    message: String,
    onClick: () -> Unit = {}
) {
    val bottomSheetDialog = BottomSheetDialog(requireContext())
    val bottomSheetBinding: LayoutBottomSheetBinding =
        LayoutBottomSheetBinding.inflate(layoutInflater, null, false)

    bottomSheetBinding.tvTitle.text = getString(title ?: R.string.title_bottom_sheet)
    bottomSheetBinding.tvMessage.text = message
    bottomSheetBinding.btnClose.text = getString(textButton ?: R.string.text_button_botton_sheet)

    bottomSheetBinding.btnClose.setOnClickListener {
        bottomSheetDialog.dismiss()
        onClick()
    }

    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.show()
}