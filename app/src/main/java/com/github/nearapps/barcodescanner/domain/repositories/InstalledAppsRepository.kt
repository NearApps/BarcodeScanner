package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.presentation.views.recyclerView.applications.ApplicationsItem

interface InstalledAppsRepository {
    suspend fun getInstalledApps(): List<ApplicationsItem>
}