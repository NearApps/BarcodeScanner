package com.github.nearapps.barcodescanner.domain.entity.product.foodProduct

import com.github.nearapps.barcodescanner.R

enum class NovaGroup(val drawableResource: Int, val descriptionStringResource: Int) {
    GROUP_1(R.drawable.nova_group_1, R.string.nova_group_description_1),
    GROUP_2(R.drawable.nova_group_2, R.string.nova_group_description_2),
    GROUP_3(R.drawable.nova_group_3, R.string.nova_group_description_3),
    GROUP_4(R.drawable.nova_group_4, R.string.nova_group_description_4),
    UNKNOWN(-1, R.string.nova_group_description_unknown)
}