package composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import data.ConversationUiState
import generated.drawable_ali
import generated.drawable_someone_else
import org.jetbrains.studchat.messagesParser.SymbolAnnotationType
import org.jetbrains.studchat.messagesParser.messageFormatter
import platform.painterResourceMultiplatform
import themes.Theme

@Composable
fun Messages(
    conversationUiState: ConversationUiState,
    scrollState: LazyListState,
    theme: State<Theme>,
) {
    val messages = conversationUiState.messages
    LazyColumn(
        reverseLayout = true,
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 150.dp),
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize(),
        state = scrollState
    ) {
        items(count = messages.size, key = { messages[it].id }) { index ->
            val msg = messages[index]
            val prevAuthor = messages.getOrNull(index - 1)?.author
            val nextAuthor = messages.getOrNull(index + 1)?.author
            val content = messages[index]
            val isFirstMessageByAuthor = prevAuthor != content.author
            val isLastMessageByAuthor = nextAuthor != content.author
            Message(
                onAuthorClick = { name -> println(name) },
                msg = msg,
                isUserMe = msg.author == "me",
                isFirstMessageByAuthor = isFirstMessageByAuthor,
                isLastMessageByAuthor = isLastMessageByAuthor,
                theme = theme
            )
        }
    }
}

@Composable
fun Message(
    onAuthorClick: (String) -> Unit,
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    theme: State<Theme>,
) {
    val borderColor = if (isUserMe) {
        theme.value.myMessageColors.messageBackground
    } else {
        theme.value.othersMessageColors.messageText
    }

    val spaceBetweenAuthors = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    Row(modifier = spaceBetweenAuthors) {

        if (isLastMessageByAuthor) {
            // Avatar
            val authorImage = painterResourceMultiplatform(msg.authorImage)
            Image(
                modifier = Modifier
                    .clickable(onClick = { onAuthorClick(msg.author) })
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, borderColor, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                painter = authorImage,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        } else {
            // Space under avatar
            Spacer(modifier = Modifier.width(74.dp))
        }
        AuthorAndTextMessage(
            msg = msg,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            isLastMessageByAuthor = isLastMessageByAuthor,
            authorClicked = onAuthorClick,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            theme = theme
        )
    }
}


@Immutable
data class Message(
    val id: String,
    val author: String,
    val content: String,
    val timestamp: String,
    val image: Any? = null,
    val authorImage: Any = if (author == "me") drawable_ali else drawable_someone_else,
)

@Composable
fun AuthorAndTextMessage(
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    authorClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    theme: State<Theme>,
) {
    Column(modifier = modifier) {
        if (isLastMessageByAuthor) {
            AuthorNameTimestamp(msg)
        }
        ChatItemBubble(msg, isUserMe, authorClicked = authorClicked, theme)
        if (isFirstMessageByAuthor) {
            // Last bubble before next author
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            // Between bubbles
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun AuthorNameTimestamp(msg: Message) {
    // Combine author and timestamp for a11y.
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = msg.author,
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = msg.timestamp,
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            modifier = Modifier.alignBy(LastBaseline),
            color = Color.Black
        )
    }
}

@Composable
fun ChatItemBubble(
    message: Message,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit,
    theme: State<Theme>,
) {

    val backgroundBubbleColor = if (isUserMe) {
        theme.value.myMessageColors.messageBackground
    } else {
        theme.value.othersMessageColors.messageBackground
    }

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape
        ) {
            ClickableMessage(
                message = message,
                isUserMe = isUserMe,
                authorClicked = authorClicked,
                theme = theme
            )
        }

        message.image?.let {
            val msgImage = painterResourceMultiplatform(it)
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = backgroundBubbleColor,
                shape = ChatBubbleShape
            ) {
                Image(
                    painter = msgImage,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(160.dp),
                    contentDescription = "Image"
                )
            }
        }
    }
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)

@Composable
fun ClickableMessage(
    message: Message,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit,
    theme: State<Theme>,
) {
    val uriHandler = LocalUriHandler.current

    val mainTextColor = when(isUserMe) {
        true -> theme.value.myMessageColors.messageText
        false -> theme.value.othersMessageColors.messageText
    }

    val styledMessage = messageFormatter(
        text = message.content,
        primary = isUserMe,
        theme = theme
    )

    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.body1.copy(color = mainTextColor),
        modifier = Modifier.padding(16.dp),
        onClick = {
            styledMessage
                .getStringAnnotations(start = it, end = it)
                .firstOrNull()
                ?.let { annotation ->
                    when (annotation.tag) {
                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
                        SymbolAnnotationType.PERSON.name -> authorClicked(annotation.item)
                        else -> Unit
                    }
                }
        }
    )
}
