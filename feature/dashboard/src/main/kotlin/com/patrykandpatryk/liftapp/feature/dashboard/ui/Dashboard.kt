package com.patrykandpatryk.liftapp.feature.dashboard.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.patrykandpatryk.liftapp.core.R
import com.patrykandpatryk.liftapp.core.ui.TopAppBar
import com.patrykandpatryk.liftapp.core.ui.topAppBarScrollBehavior

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Dashboard(modifier: Modifier = Modifier) {

    val topAppBarScrollBehavior = topAppBarScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.route_dashboard),
                scrollBehavior = topAppBarScrollBehavior,
            )
        },
    ) {}
}
