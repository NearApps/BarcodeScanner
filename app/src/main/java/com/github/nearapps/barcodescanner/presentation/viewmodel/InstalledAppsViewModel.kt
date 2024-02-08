package com.github.nearapps.barcodescanner.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.github.nearapps.barcodescanner.domain.repositories.InstalledAppsRepository
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.applications.ApplicationsItem
import kotlinx.coroutines.Dispatchers

/**
 * Liste les applications installées sur l'appareil.
 */
class InstalledAppsViewModel(private val installedAppsRepository: InstalledAppsRepository): ViewModel() {
    val installedApps: LiveData<List<ApplicationsItem>> = liveData(Dispatchers.IO) {
        emit(installedAppsRepository.getInstalledApps())
    }
}