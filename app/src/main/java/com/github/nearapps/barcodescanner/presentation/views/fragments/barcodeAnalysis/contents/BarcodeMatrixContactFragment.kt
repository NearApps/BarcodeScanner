package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.contents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.common.extensions.convertToString
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeMatrixContactBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.customView.BarcodeParsedView
import com.google.zxing.client.result.AddressBookParsedResult
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ParsedResultType

/**
 * A simple [Fragment] subclass.
 */
class BarcodeMatrixContactFragment : AbstractBarcodeMatrixFragment() {

    private var _binding: FragmentBarcodeMatrixContactBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeMatrixContactBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(product: BarcodeAnalysis, parsedResult: ParsedResult) {
        if(parsedResult is AddressBookParsedResult && parsedResult.type == ParsedResultType.ADDRESSBOOK) {
            configureName(parsedResult.names ?: parsedResult.nicknames)
            configureOrganization(parsedResult.org)
            configureTitle(parsedResult.title)
            configurePhone(parsedResult.phoneNumbers, parsedResult.phoneTypes)
            configureMail(parsedResult.emails, parsedResult.emailTypes)
            configureAddress(parsedResult.addresses)
            configureNotes(parsedResult.note)
        } else {
            viewBinding.root.visibility = View.GONE
        }
    }

    private fun configureName(names: Array<String?>?) {
        viewBinding.fragmentBarcodeMatrixContactNameLayout.setContentsText(names?.convertToString())
    }

    private fun configureOrganization(org: String?) {
        viewBinding.fragmentBarcodeMatrixContactOrganizationLayout.setContentsText(org)
    }

    private fun configureTitle(title: String?) {
        viewBinding.fragmentBarcodeMatrixContactTitleLayout.setContentsText(title)
    }

    private fun configurePhone(phoneNumbers: Array<String?>?, phoneTypes: Array<String?>?) {
        configureContact(
            view = viewBinding.fragmentBarcodeMatrixContactPhoneLayout,
            contacts = phoneNumbers,
            types = phoneTypes
        )
    }

    private fun configureMail(mails: Array<String?>?, mailTypes: Array<String?>?) {
        configureContact(
            view = viewBinding.fragmentBarcodeMatrixContactEmailLayout,
            contacts = mails,
            types = mailTypes
        )
    }

    private fun configureAddress(addresses: Array<String?>?) {
        viewBinding.fragmentBarcodeMatrixContactAddressLayout.setContentsText(addresses?.convertToString("\n"))
    }

    private fun configureNotes(notes: String?) {
        viewBinding.fragmentBarcodeMatrixContactNotesLayout.setContentsText(notes)
    }

    private fun configureContact(
        view: BarcodeParsedView,
        contacts: Array<String?>?,
        types: Array<String?>?
    ) {
        if(contacts.isNullOrEmpty()){
            view.visibility = View.GONE
        } else {

            val stringBuilder = StringBuilder()

            for(i in contacts.indices) {
                if(!contacts[i].isNullOrEmpty()) {
                    stringBuilder.append(contacts[i])

                    if(types != null && types.size > i) {
                        stringBuilder.append(" (${types[i]})")
                    }

                    if(i<contacts.size-1) {
                        stringBuilder.append("\n")
                    }
                }
            }

            val result = stringBuilder.toString()
            if(result.isNotEmpty()) {
                view.setContentsText(result)
            }
        }
    }
}