package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.t_bank.R
import com.example.t_bank.databinding.FragmentProfileRedactionBinding
import com.google.android.material.snackbar.Snackbar

class ProfileRedactionFragment : Fragment() {

    private var _binding: FragmentProfileRedactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileRedactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            saveProfileChanges()
        }
    }

    private fun saveProfileChanges() {
        val phoneNumber = binding.tilPhoneNumber.editText?.text.toString().trim()
        val newPassword = binding.tilNewPassword.editText?.text.toString().trim()
        val oldPassword = binding.tilOldPassword.editText?.text.toString().trim()

        if (phoneNumber.isEmpty()) {
            binding.tilPhoneNumber.error = getString(R.string.error_empty_phone)
            return
        }

        if (newPassword.isEmpty()) {
            binding.tilNewPassword.error = getString(R.string.error_empty_new_password)
            return
        }

        if (oldPassword.isEmpty()) {
            binding.tilOldPassword.error = getString(R.string.error_empty_old_password)
            return
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            binding.tilPhoneNumber.error = getString(R.string.error_invalid_phone)
            return
        }


        Snackbar.make(binding.root, getString(R.string.changes_saved), Snackbar.LENGTH_SHORT).show()
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val cleaned = phoneNumber.replace("[^\\d]".toRegex(), "")
        return cleaned.matches(Regex("^7\\d{10}$"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
