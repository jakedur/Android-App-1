package com.example.lifecyclemanagement

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.lifecyclemanagement.databinding.FragmentSignupBinding
import com.google.android.material.textfield.TextInputEditText


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignupFragment : Fragment() {

    //    private val userModel: UserViewModel by activityViewModels()
    private var _binding: FragmentSignupBinding? = null
    private lateinit var firstNameText: TextInputEditText
    private lateinit var middleNameText: TextInputEditText
    private lateinit var lastNameText: TextInputEditText
    private var profilePicture: Bitmap? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        firstNameText = _binding!!.firstNameInput
        middleNameText = _binding!!.middleNameInput
        lastNameText = _binding!!.lastNameInput
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            val saveData: User? = savedInstanceState.getParcelable("SaveData")
            if (saveData != null) {
                firstNameText.setText(saveData.firstName)
                middleNameText.setText(saveData.middleName)
                lastNameText.setText(saveData.lastName)
                profilePicture = saveData.profilePicture
            }
        }

        binding.photoButton.setOnClickListener {
            if (context?.checkSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        cameraActivity.launch(intent)
                    }
                } catch (e: java.lang.Exception) {
                    println(e.message)
                }
            } else {
                requestPermissions(arrayOf("android.permission.CAMERA"), 1)
                Toast.makeText(
                    context,
                    "Please enable camera permission to take profile picture and press take profile picture again",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.submitButton.setOnClickListener {

            val user =
                User(
                    firstNameText.text.toString(),
                    middleNameText.text.toString(),
                    lastNameText.text.toString(),
                    profilePicture
                )
            UserSingleton.UserObject = user
//            userModel.currentUser.value = user
            val newActivity = Intent(activity, MainActivity::class.java)
            // I was going to pass data, but decided singleton is best
            activity?.startActivity(newActivity)
        }
    }

    private val cameraActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 33) {
                    profilePicture =
                        result.data!!.getParcelableExtra("data", Bitmap::class.java)
                } else {
                    profilePicture =
                        result.data!!.getParcelableExtra<Bitmap>("data")
                }
            }
        }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        val saveData =
            User(
                firstNameText.text.toString(),
                middleNameText.text.toString(),
                lastNameText.text.toString(),
                profilePicture
            )
        savedInstanceState.putParcelable("SaveData", saveData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}