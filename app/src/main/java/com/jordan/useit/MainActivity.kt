package com.jordan.useit

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jordan.useit.databinding.ActivityMainBinding
import com.jordan.useit.databinding.DialogAddProjectBinding
import com.jordan.useit.databinding.DialogNewsletterBinding
import org.osmdroid.config.Configuration
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private var userRole: String? = null
    private val IMAGE_PICK_CODE = 1001
    private val PERMISSION_CODE = 1002
    private lateinit var addProjectDialog: AlertDialog
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // OSMdroid config setup
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        Configuration.getInstance().userAgentValue = ctx.packageName
        Configuration.getInstance().osmdroidBasePath = File(ctx.cacheDir, "osmdroid")

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Check if user is signed in
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        } else {
            fetchUserRole()
        }

        // Setup view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        // Setup navigation
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_projects, R.id.nav_donations, R.id.nav_contact, R.id.nav_find_us, R.id.nav_about_us), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun fetchUserRole() {
        // Fetch user role from Firestore and setup FAB icon based on role
        val userId = auth.currentUser?.uid
        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    userRole = document.getString("role")
                    setupFab() // Call setupFab once userRole is known
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to retrieve user role", Toast.LENGTH_SHORT).show()
                }
        } else {
            setupFab() // Anonymous or no user role available
        }
    }

    private fun setupFab() {
        // Set FAB icon and action based on user role
        if (userRole == "admin") {
            binding.appBarMain.fab.setImageResource(R.drawable.baseline_add_24)
            binding.appBarMain.fab.setOnClickListener {
                showAddProjectDialog()
            }
        } else {
            //binding.appBarMain.fab.setImageResource(R.drawable.baseline_email_24)
            binding.appBarMain.fab.setOnClickListener {
                showNewsletterDialog()
            }
        }
    }

    private fun showNewsletterDialog() {
        val dialogBinding = DialogNewsletterBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setTitle("Sign Up for Newsletter")
            .setView(dialogBinding.root)
            .setPositiveButton("Submit") { dialogInterface, _ ->
                val email = dialogBinding.emailEditText.text.toString()
                // Handle email submission (currently non-functional)
                dialogInterface.dismiss()
            }
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    private fun showAddProjectDialog() {
        val dialogBinding = DialogAddProjectBinding.inflate(layoutInflater)

        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Add New Project")
            .setView(dialogBinding.root)
            .setPositiveButton("Add", null)
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }

        addProjectDialog = dialogBuilder.create()

        // Handle image selection
        dialogBinding.selectImageButton.setOnClickListener {
            selectImageFromGallery()
        }

        // Set up positive button click after dialog is shown to prevent auto-dismiss
        addProjectDialog.setOnShowListener {
            val addButton = addProjectDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            addButton.setOnClickListener {
                val title = dialogBinding.titleEditText.text.toString()
                val description = dialogBinding.descriptionEditText.text.toString()
                if (title.isNotBlank() && description.isNotBlank() && selectedImageUri != null) {
                    uploadProjectImageAndSaveData(title, description)
                    addProjectDialog.dismiss()
                } else {
                    Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show()
                }
            }
        }

        addProjectDialog.show()
    }

    private fun selectImageFromGallery() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_CODE
            )
        } else {
            // Permission granted
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                pickImageFromGallery()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val dialogBinding = DialogAddProjectBinding.inflate(layoutInflater)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImageUri = data?.data
            // Update the image preview in the dialog
            val imageView = addProjectDialog.findViewById<ImageView>(R.id.imagePreview)
            imageView?.visibility = View.VISIBLE
            imageView?.setImageURI(selectedImageUri)
        }
    }

    private fun uploadProjectImageAndSaveData(title: String, description: String) {
        val storageRef = FirebaseStorage.getInstance().reference.child("project_images/${UUID.randomUUID()}")
        selectedImageUri?.let { uri ->
            storageRef.putFile(uri)
                .addOnSuccessListener {
                    // Get the download URL
                    storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        // Save project data to Firestore
                        saveProjectData(title, description, downloadUrl.toString())
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveProjectData(title: String, description: String, imageUrl: String) {
        val projectRef = FirebaseFirestore.getInstance().collection("projects").document()
        val projectData = mapOf(
            "title" to title,
            "description" to description,
            "imageUrl" to imageUrl,
            "timestamp" to FieldValue.serverTimestamp()
        )
        projectRef.set(projectData)
            .addOnSuccessListener {
                Toast.makeText(this, "Project added successfully", Toast.LENGTH_SHORT).show()
                // Refresh the projects list if necessary
            }
            .addOnFailureListener { e ->
                // Handle failure
                Toast.makeText(this, "Failed to add project", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                // Logout the user
                auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
