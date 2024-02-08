package com.github.nearapps.barcodescanner.data.repositories

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import com.github.nearapps.barcodescanner.domain.repositories.InstalledAppsRepository
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.applications.ApplicationsItem

class InstalledAppsRepositoryImpl(private val context: Context): InstalledAppsRepository {

    override suspend fun getInstalledApps(): List<ApplicationsItem> {
        val pm: PackageManager = context.packageManager
        return listInstalledApps(pm, listResolveInfo(pm)).sortedBy { it.title }
    }

    private fun listInstalledApps(pm: PackageManager, packages: List<ResolveInfo>): List<ApplicationsItem> {
        val applications = mutableListOf<ApplicationsItem>()
        for (pkg in packages) {
            val appName = pkg.activityInfo.applicationInfo.loadLabel(pm).toString()
            val packageName = pkg.activityInfo.packageName
            val appIcon = pm.getApplicationIcon(pkg.activityInfo.applicationInfo)

            applications.add(ApplicationsItem(appName, packageName, appIcon))
        }
        return applications
    }

    @Suppress("DEPRECATION")
    private fun listResolveInfo(pm: PackageManager): List<ResolveInfo> {
        val query = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> pm.queryIntentActivities(query, PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong()))
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> pm.queryIntentActivities(query, PackageManager.MATCH_ALL)
            else -> pm.queryIntentActivities(query, 0)
        }
    }
}