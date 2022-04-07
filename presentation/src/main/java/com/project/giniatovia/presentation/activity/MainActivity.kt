package com.project.giniatovia.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.project.giniatovia.presentation.R
import com.project.giniatovia.presentation.fragment.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        // val fingerprints = VKUtils.getCertificateFingerprint(this, this.packageName)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.container, LoginFragment())
            }
        }
    }
}
