package composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import data.AdditionalUiState
import data.colleagueProfile
import data.meProfile
import generated.drawable_jetchat_icon_mpp
import platform.painterResourceMultiplatform
import themes.toBoolean

@Composable
fun JetchatDrawer(
    onProfileClicked: (String) -> Unit,
    onChatClicked: (String) -> Unit,
    onThemeChange: (Boolean) -> Unit,
    uiState: AdditionalUiState,
) {
    // Use statusBarsHeight() to add a spacer which pushes the drawer content
    // below the status bar (y-axis)
    Spacer(Modifier.height(3.dp))
    DrawerHeader()
    DividerItem()
    DrawerItemHeader("Chats")
    ChatItem("composers", true) { onChatClicked("composers") }
    ChatItem("droidcon-nyc", false) { onChatClicked("droidcon-nyc") }
    DividerItem(modifier = Modifier.padding(horizontal = 28.dp))
    DrawerItemHeader("Recent Profiles")
    ProfileItem("Ali Conors (you)", meProfile.photo) { onProfileClicked(meProfile.userId) }
    ProfileItem("Taylor Brooks", colleagueProfile.photo) {
        onProfileClicked(colleagueProfile.userId)
    }
    Box(
        Modifier
            .defaultMinSize(300.dp, 48.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clip(CircleShape)
        ) {
            val checkedState by uiState.themeMode.collectAsState()
            Switch(
                checked = checkedState.toBoolean(),
                onCheckedChange = {
                    onThemeChange(it)
                }
            )
        }
    }
}

@Composable
private fun DrawerHeader() {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        JetchatIcon(
            contentDescription = "Open navigation drawer",
            modifier = Modifier
                .size(64.dp)
                .padding(16.dp)
        )
    }
}

@Composable
private fun DrawerItemHeader(text: String) {
    Box(
        modifier = Modifier
            .heightIn(min = 52.dp)
            .padding(horizontal = 28.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ChatItem(text: String, selected: Boolean, onChatClicked: () -> Unit) {
    val background = if (selected) {
        Modifier.background(MaterialTheme.colorScheme.primaryContainer)
    } else {
        Modifier
    }
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(CircleShape)
            .then(background)
            .clickable(onClick = onChatClicked),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconTint = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }
        Icon(
            painter = painterResourceMultiplatform(drawable_jetchat_icon_mpp),
            tint = iconTint,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            contentDescription = null
        )
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
private fun ProfileItem(text: String, profilePic: Any?, onProfileClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(CircleShape)
            .clickable(onClick = onProfileClicked),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val paddingSizeModifier = Modifier
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .size(24.dp)
        if (profilePic != null) {
            Image(
                painter = painterResourceMultiplatform(profilePic),
                modifier = paddingSizeModifier.then(Modifier.clip(CircleShape)),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        } else {
            Spacer(modifier = paddingSizeModifier)
        }
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun DividerItem(modifier: Modifier = Modifier) {
    // TODO (M3): No Divider, replace when available
    Divider(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}