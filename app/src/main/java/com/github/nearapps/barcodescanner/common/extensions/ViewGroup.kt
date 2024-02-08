package com.github.nearapps.barcodescanner.common.extensions

import android.animation.LayoutTransition
import android.view.ViewGroup

/**
 * Un problème de transition se produit lors de l'ouverture et la fermeture des ExpandableViews
 * présents dans une NestedScrollView. Pour corriger ce problème, cette méthode doit être appelé
 * par la vue contenant les ExpandableViews.
 */
fun ViewGroup.fixAnimateLayoutChangesInNestedScroll(){
    layoutTransition = LayoutTransition().apply {
        disableTransitionType(LayoutTransition.DISAPPEARING)
        setAnimateParentHierarchy(false)
        enableTransitionType(LayoutTransition.CHANGING)
    }
}