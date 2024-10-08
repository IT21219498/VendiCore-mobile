package com.example.vendicore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.vendicore.R

class PaymentDetailsDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_payment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextCardNumber: EditText = view.findViewById(R.id.editTextCardNumber)
        val editTextExpiryDate: EditText = view.findViewById(R.id.editTextExpiryDate)
        val editTextCVV: EditText = view.findViewById(R.id.editTextCVV)
        val buttonSubmitPayment: Button = view.findViewById(R.id.buttonSubmitPayment)

        buttonSubmitPayment.setOnClickListener {
            val cardNumber = editTextCardNumber.text.toString()
            val expiryDate = editTextExpiryDate.text.toString()
            val cvv = editTextCVV.text.toString()

            if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Handle payment submission logic here
                Toast.makeText(requireContext(), "Payment Submitted", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }
}