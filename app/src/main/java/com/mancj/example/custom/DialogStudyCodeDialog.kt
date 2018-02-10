package com.mancj.example.custom

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mancj.example.R


class DialogStudyCodeDialog : DialogFragment() {

    private val dataList = mutableListOf("Simvastatin", "Carrot Daucus carota", "Sodium Fluoride", "White Kidney Beans", "Salicylic Acid", "cetirizine hydrochloride", "Mucor racemosus", "Thymol", "TOLNAFTATE", "Albumin Human")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.view_dialog_studycode, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        edStudyCode.setRoundedSearchBarEnabled(true)
        edStudyCode.setPlaceHolder("ทั้งหมด")
        edStudyCode.disableSearch()
        edStudyCode.setNavButtonEnabled(false)
        val customSuggestionsAdapter = CustomSuggestionsAdapter2(inflater)
        customSuggestionsAdapter.suggestions = dataList
        edStudyCode.setCustomSuggestionAdapter(customSuggestionsAdapter)
        edStudyCode.showSuggestionsList()
        isCancelable = true*/
    }

    companion object {

        fun newInstance(): DialogStudyCodeDialog {
            val fragment = DialogStudyCodeDialog()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    class Builder {

        fun setDataList(dataList: ArrayList<String>): Builder {
            return this
        }

        fun build(): DialogStudyCodeDialog {
            return DialogStudyCodeDialog.newInstance()
        }
    }

}