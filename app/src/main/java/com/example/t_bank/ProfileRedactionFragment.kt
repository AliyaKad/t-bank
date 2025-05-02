package com.example.t_bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        println("Phone: $phoneNumber, Old Password: $oldPassword, New Password: $newPassword")

        Snackbar.make(binding.root, getString(R.string.changes_saved), Snackbar.LENGTH_SHORT).show()
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("^\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}$"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
