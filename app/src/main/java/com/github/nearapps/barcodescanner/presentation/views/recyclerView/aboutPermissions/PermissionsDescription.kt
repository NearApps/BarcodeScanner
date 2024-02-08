package com.github.nearapps.barcodescanner.presentation.views.recyclerView.aboutPermissions

import com.github.nearapps.barcodescanner.R

enum class PermissionsDescription(val nameResource: Int, val descriptionResource: Int) {
    CAMERA(R.string.permission_camera_label, R.string.permission_camera_description_label),
    INTERNET(R.string.permission_internet_label, R.string.permission_internet_description_label),
    CONTACT(R.string.permission_contact_label, R.string.permission_contact_description_label),
    LOCATION(R.string.permission_location_label, R.string.permission_location_description_label),
    WIFI(R.string.permission_wifi_label, R.string.permission_wifi_description_label),
    VIBRATE(R.string.permission_vibrate_label, R.string.permission_vibrate_description_label),
    //QUERY_ALL_PACKAGES(R.string.permission_query_all_packages_label, R.string.permission_query_all_packages_description_label)
}