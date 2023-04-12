package data

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import composables.Message
import generated.drawable_ali
import generated.drawable_someone_else
import generated.drawable_sticker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import platform.generateUuid
import themes.ThemeMode

private val initialMessages = listOf(
    Message(
        generateUuid(),
        "me",
        "Check it out!",
        "8:07 PM"
    ),
    Message(
        generateUuid(),
        "me",
        "Thank you!",
        "8:06 PM",
        drawable_sticker
    ),
    Message(
        generateUuid(),
        "Taylor Brooks",
        "You can use all the same stuff",
        "8:05 PM"
    ),
    Message(
        generateUuid(),
        "Taylor Brooks",
        "@aliconors Take a look at the `Flow.collectAsState()` APIs",
        "8:05 PM"
    ),
    Message(
        generateUuid(),
        "John Glenn",
        "Compose newbie as well, have you looked at the JetNews sample? Most blog posts end up " +
                "out of date pretty fast but this sample is always up to date and deals with async " +
                "data loading (it's faked but the same idea applies) \uD83D\uDC49" +
                "https://github.com/android/compose-samples/tree/master/JetNews",
        "8:04 PM"
    ),
    Message(
        generateUuid(),
        "me",
        "Compose newbie: I’ve scourged the internet for tutorials about async data loading " +
                "but haven’t found any good ones. What’s the recommended way to load async " +
                "data and emit composable widgets?",
        "8:03 PM"
    )
)

val exampleUiState = ConversationUiState(
    initialMessages = initialMessages,
    channelName = "#composers",
    channelMembers = 42
)

/**
 * Example colleague profile
 */
val colleagueProfile = ProfileScreenState(
    userId = "12345",
    photo = drawable_someone_else,
    name = "Taylor Brooks",
    status = "Away",
    displayName = "taylor",
    position = "Senior Android Dev at Openlane",
    twitter = "twitter.com/taylorbrookscodes",
    timeZone = "12:25 AM local time (Eastern Daylight Time)",
    commonChannels = "2"
)

/**
 * Example "me" profile.
 */
val meProfile = ProfileScreenState(
    userId = "me",
    photo = drawable_ali,
    name = "Ali Conors",
    status = "Online",
    displayName = "aliconors",
    position = "Senior Android Dev at Yearin\nGoogle Developer Expert",
    twitter = "twitter.com/aliconors",
    timeZone = "In your timezone",
    commonChannels = null
)

@Immutable
data class ProfileScreenState(
    val userId: String,
    val photo: Any?,
    val name: String,
    val status: String,
    val displayName: String,
    val position: String,
    val twitter: String = "",
    val timeZone: String?, // Null if me
    val commonChannels: String?, // Null if me
) {
    fun isMe() = userId == meProfile.userId
}

class ConversationUiState(
    val channelName: String,
    val channelMembers: Int,
    initialMessages: List<Message>,
) {
    private val _messages: MutableList<Message> =
        mutableStateListOf(*initialMessages.toTypedArray())
    val messages: List<Message> = _messages

    fun addMessage(msg: Message) {
        _messages.add(0, msg) // Add to the beginning of the list
    }
}

@Stable
class AdditionalUiState {
    private val _themeMode: MutableStateFlow<ThemeMode> = MutableStateFlow(ThemeMode.LIGHT)
    val themeMode: StateFlow<ThemeMode> = _themeMode
    private val _drawerShouldBeOpened: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val drawerShouldBeOpened: StateFlow<Boolean> = _drawerShouldBeOpened
    fun openDrawer() {
        _drawerShouldBeOpened.value = true
    }

    fun resetOpenDrawerAction() {
        _drawerShouldBeOpened.value = false
    }

    fun switchTheme(theme: ThemeMode) {
        _themeMode.value = theme
    }
}