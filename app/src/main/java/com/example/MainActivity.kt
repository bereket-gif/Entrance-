package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.*
import com.example.ui.*
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.safeDrawing
                ) { innerPadding ->
                    EthioMatricApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun EthioMatricApp(
    modifier: Modifier = Modifier,
    quizViewModel: QuizViewModel = viewModel()
) {
    val uiState by quizViewModel.uiState.collectAsStateWithLifecycle()
    val bookmarks by quizViewModel.allBookmarks.collectAsStateWithLifecycle()
    val history by quizViewModel.allHistory.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBg)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
            AppHeader(
                currentScreen = uiState.currentScreen,
                title = when (uiState.currentScreen) {
                    Screen.DASHBOARD -> "Ethio Matric"
                    Screen.QUIZ -> "${uiState.selectedSubject} Quiz"
                    Screen.RESULT -> "Session Complete"
                    Screen.BOOKMARKS -> "Saved Bookmarks"
                    Screen.HISTORY -> "Performance Logs"
                },
                timerText = if (uiState.currentScreen == Screen.QUIZ) formatTime(uiState.elapsedSeconds) else null,
                onBackClicked = {
                    when (uiState.currentScreen) {
                        Screen.QUIZ -> quizViewModel.resetApp()
                        Screen.RESULT -> quizViewModel.resetApp()
                        else -> quizViewModel.navigateTo(Screen.DASHBOARD)
                    }
                }
            )

            // Screen Content with Smooth Animated Transitions
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Crossfade(targetState = uiState.currentScreen, label = "ScreenTransition") { screen ->
                    when (screen) {
                        Screen.DASHBOARD -> {
                            DashboardScreen(
                                onSubjectSelected = { quizViewModel.startQuiz(it) },
                                onNavigateToScreen = { quizViewModel.navigateTo(it) },
                                bookmarksCount = bookmarks.size,
                                historyCount = history.size
                            )
                        }
                        Screen.QUIZ -> {
                            QuizScreen(
                                uiState = uiState,
                                onOptionSelected = { quizViewModel.selectOption(it) },
                                onVerifyClicked = { quizViewModel.verifyAnswer() },
                                onNextClicked = { quizViewModel.nextQuestion() },
                                onDotClicked = { quizViewModel.jumpToQuestion(it) },
                                onBookmarkToggled = { quizViewModel.toggleBookmark() }
                            )
                        }
                        Screen.RESULT -> {
                            ResultScreen(
                                uiState = uiState,
                                onReturnDashboard = { quizViewModel.resetApp() }
                            )
                        }
                        Screen.BOOKMARKS -> {
                            BookmarksScreen(
                                bookmarks = bookmarks,
                                onDeleteBookmark = { quizViewModel.deleteBookmarkDirectly(it) }
                            )
                        }
                        Screen.HISTORY -> {
                            HistoryScreen(
                                historyList = history,
                                onClearHistory = { quizViewModel.clearAllHistory() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppHeader(
    currentScreen: Screen,
    title: String,
    timerText: String?,
    onBackClicked: () -> Unit
) {
    Surface(
        color = SlateSurface,
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                if (currentScreen != Screen.DASHBOARD) {
                    IconButton(
                        onClick = onBackClicked,
                        modifier = Modifier
                            .testTag("back_button")
                            .padding(end = 8.dp)
                            .size(36.dp)
                            .background(SlateBg, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("app_title")
                )
            }

            if (timerText != null) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SlateBg),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, BlueAccent),
                    modifier = Modifier.testTag("timer")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh, // Placeholder for timing clock
                            contentDescription = "Timer Icon",
                            tint = BlueAccent,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = timerText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueAccent,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(
    onSubjectSelected: (String) -> Unit,
    onNavigateToScreen: (Screen) -> Unit,
    bookmarksCount: Int,
    historyCount: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Title: Subjects Available
        item {
            Text(
                text = "Academic Subjects",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        // Subjects Grid
        val subjectsList = listOf(
            SubjectItem(
                "Mathematics",
                "Algebra, Calculus & Extrema",
                "${QuestionBank.questions.count { it.subject == "Mathematics" }} Drill Questions",
                EmeraldAccent,
                Icons.Default.Star
            ),
            SubjectItem(
                "Biology",
                "Cell Physiology, Respiration & Genetics",
                "${QuestionBank.questions.count { it.subject == "Biology" }} Drill Questions",
                BlueAccent,
                Icons.Default.PlayArrow
            ),
            SubjectItem(
                "Aptitude",
                "Series Progression & Analytical Logic",
                "${QuestionBank.questions.count { it.subject == "Aptitude" }} Drill Questions",
                Color(0xFFF59E0B),
                Icons.Default.Info
            ),
            SubjectItem(
                "English",
                "Grammatical Standards & Commas",
                "${QuestionBank.questions.count { it.subject == "English" }} Drill Questions",
                Color(0xFFEC4899),
                Icons.Default.List
            ),
            SubjectItem(
                "Physics",
                "Mechanics, Thermodynamics & Electromagnetism",
                "${QuestionBank.questions.count { it.subject == "Physics" }} Drill Questions",
                Color(0xFF8B5CF6),
                Icons.Default.Build
            ),
            SubjectItem(
                "Chemistry",
                "Atomic Theory, Bonding & Redox Reactions",
                "${QuestionBank.questions.count { it.subject == "Chemistry" }} Drill Questions",
                Color(0xFF06B6D4),
                Icons.Default.Refresh
            )
        )

        items(subjectsList) { subject ->
            Card(
                onClick = { onSubjectSelected(subject.name) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("subject_card_${subject.name.lowercase()}"),
                border = BorderStroke(1.dp, SlateBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(subject.accentColor.copy(alpha = 0.12f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = subject.icon,
                                contentDescription = subject.name,
                                tint = subject.accentColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column {
                            Text(
                                text = subject.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = subject.subtitle,
                                fontSize = 12.sp,
                                color = TextMuted,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .background(SlateBg, RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = subject.questionsCount,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextMuted
                        )
                    }
                }
            }
        }

        // Combined standard subjects Practice Card
        item {
            Card(
                onClick = { onSubjectSelected("All Subjects") },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SlateBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("all_subjects_card"),
                border = BorderStroke(1.dp, TextMuted.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color.White.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Mix",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Comprehensive Mix",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "100 Mixed Shuffled Exam Questions",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start Mix",
                        tint = EmeraldAccent
                    )
                }
            }
        }

        // Section: Analytics & Saved
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Bookmarked card
                Card(
                    onClick = { onNavigateToScreen(Screen.BOOKMARKS) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SlateSurface),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("bookmarks_nav_card"),
                    border = BorderStroke(1.dp, SlateBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Bookmarks",
                                tint = EmeraldAccent,
                                modifier = Modifier.size(20.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .background(EmeraldAccent.copy(alpha = 0.12f), CircleShape)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = bookmarksCount.toString(),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldAccent
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "Bookmarks",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Saved for review",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }
                }

                // History card
                Card(
                    onClick = { onNavigateToScreen(Screen.HISTORY) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SlateSurface),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("history_nav_card"),
                    border = BorderStroke(1.dp, SlateBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "History Logs",
                                tint = BlueAccent,
                                modifier = Modifier.size(20.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .background(BlueAccent.copy(alpha = 0.12f), CircleShape)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = historyCount.toString(),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BlueAccent
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "Performance",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Score histories",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}

data class SubjectItem(
    val name: String,
    val subtitle: String,
    val questionsCount: String,
    val accentColor: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun QuizScreen(
    uiState: QuizUiState,
    onOptionSelected: (Int) -> Unit,
    onVerifyClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onDotClicked: (Int) -> Unit,
    onBookmarkToggled: () -> Unit
) {
    val scrollState = rememberScrollState()
    val activeQuestion = uiState.quizQuestions.getOrNull(uiState.currentQuestionIndex)

    if (activeQuestion == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = EmeraldAccent)
        }
        return;
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Linear Progress Bar
        val progressPercentByDot = (uiState.currentQuestionIndex + 1).toFloat() / uiState.quizQuestions.size
        LinearProgressIndicator(
            progress = progressPercentByDot,
            trackColor = SlateSurface,
            color = EmeraldAccent,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .testTag("progress_bar")
        )

        // Progress Nav Dots (Interactive dot progress header)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .horizontalScroll(rememberScrollState())
                .testTag("progress_nav"),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiState.quizQuestions.forEachIndexed { idx, _ ->
                val result = uiState.questionResults.getOrNull(idx)
                val isActive = idx == uiState.currentQuestionIndex

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                result == true -> EmeraldAccent
                                result == false -> RedAccent
                                isActive -> SlateSurface
                                else -> SlateSurface
                            }
                        )
                        .border(
                            width = if (isActive) 2.dp else 1.dp,
                            color = if (isActive) Color.White else SlateBorder,
                            shape = CircleShape
                        )
                        .clickable { onDotClicked(idx) }
                        .testTag("dot_$idx"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (idx + 1).toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (result != null) Color.White else TextMain
                    )
                }
            }
        }

        // Scrollable Question Main Body
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Subject tag & Bookmarking icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(BlueAccent.copy(alpha = 0.12f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = activeQuestion.subject.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueAccent
                    )
                }

                IconButton(
                    onClick = onBookmarkToggled,
                    modifier = Modifier.testTag("bookmark_toggle")
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Save question",
                        tint = if (uiState.isCurrentQuestionBookmarked) EmeraldAccent else Color.White.copy(alpha = 0.35f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Question text
            Text(
                text = activeQuestion.question,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                lineHeight = 26.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("question_text")
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Options List
            Column(
                modifier = Modifier.fillMaxWidth().testTag("options_group"),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                activeQuestion.options.forEachIndexed { optIdx, optionText ->
                    val isSelected = uiState.selectedOptionIndex == optIdx
                    val isVerified = uiState.isAnswerVerified
                    val isCorrectIdx = optIdx == activeQuestion.correct

                    val borderBrushColor = when {
                        isVerified && isCorrectIdx -> EmeraldAccent
                        isVerified && isSelected && !isCorrectIdx -> RedAccent
                        isSelected -> BlueAccent
                        else -> SlateBorder
                    }

                    val bgTint = when {
                        isVerified && isCorrectIdx -> EmeraldAccent.copy(alpha = 0.08f)
                        isVerified && isSelected && !isCorrectIdx -> RedAccent.copy(alpha = 0.08f)
                        isSelected -> BlueAccent.copy(alpha = 0.08f)
                        else -> SlateSurface
                    }

                    Card(
                        onClick = { onOptionSelected(optIdx) },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = bgTint),
                        border = BorderStroke(if (isSelected || (isVerified && isCorrectIdx)) 2.dp else 1.dp, borderBrushColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("option_$optIdx")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Circular Bullet (A, B, C, D)
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(
                                        when {
                                            isVerified && isCorrectIdx -> EmeraldAccent
                                            isVerified && isSelected && !isCorrectIdx -> RedAccent
                                            isSelected -> BlueAccent
                                            else -> SlateBg
                                        },
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ('A' + optIdx).toString(),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            Text(
                                text = optionText,
                                fontSize = 15.sp,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )

                            // Status Icon (Check/Close)
                            if (isVerified) {
                                if (isCorrectIdx) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Correct",
                                        tint = EmeraldAccent
                                    )
                                } else if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Incorrect",
                                        tint = RedAccent
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Slide out step explanation Box
            AnimatedVisibility(
                visible = uiState.isAnswerVerified,
                enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .testTag("explanation_box"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SlateSurface),
                    border = BorderStroke(1.dp, SlateBorder)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 16.dp, horizontal = 16.dp)
                                .drawBehind {
                                    drawLine(
                                        color = BlueAccent,
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        strokeWidth = 4.dp.toPx()
                                    )
                                }
                                .padding(start = 12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Info",
                                    tint = BlueAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Explanation & Steps",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = BlueAccent
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = activeQuestion.explanation,
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                color = TextMain
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Action Trigger Button at bottom
        Button(
            onClick = {
                if (uiState.isAnswerVerified) {
                    onNextClicked()
                } else {
                    onVerifyClicked()
                }
            },
            enabled = uiState.selectedOptionIndex != null,
            colors = ButtonDefaults.buttonColors(
                containerColor = EmeraldAccent,
                disabledContainerColor = SlateSurface
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("action_button")
        ) {
            val isLast = uiState.currentQuestionIndex == uiState.quizQuestions.size - 1
            Text(
                text = when {
                    !uiState.isAnswerVerified -> "Verify Answer"
                    isLast -> "View Final Stats"
                    else -> "Next Question"
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (uiState.selectedOptionIndex != null) Color.White else TextMuted
            )
        }
    }
}

@Composable
fun ResultScreen(
    uiState: QuizUiState,
    onReturnDashboard: () -> Unit
) {
    val scrollState = rememberScrollState()
    val accuracy = if (uiState.quizQuestions.isNotEmpty()) {
        (uiState.score * 100) / uiState.quizQuestions.size
    } else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Academic Drill Completed",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Review your credentials and exam performance details",
                fontSize = 13.sp,
                color = TextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Large Circular Score Gauge
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .drawBehind {
                        drawCircle(
                            color = SlateSurface,
                            radius = size.minDimension / 2,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 16.dp.toPx())
                        )
                        // Accent colored sweeps
                        drawArc(
                            color = if (accuracy >= 80) EmeraldAccent else if (accuracy >= 50) BlueAccent else RedAccent,
                            startAngle = -90f,
                            sweepAngle = (accuracy.toFloat() / 100f) * 360f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(
                                width = 16.dp.toPx(),
                                cap = androidx.compose.ui.graphics.StrokeCap.Round
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${uiState.score}/${uiState.quizQuestions.size}",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier.testTag("final_score")
                    )
                    Text(
                        text = "$accuracy% Score",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(34.dp))

            // Sub Stats Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = SlateSurface),
                    border = BorderStroke(1.dp, SlateBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("TIME SPENT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(formatTime(uiState.elapsedSeconds), fontSize = 18.sp, fontWeight = FontWeight.Black, color = BlueAccent)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = SlateSurface),
                    border = BorderStroke(1.dp, SlateBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("SUBJECT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = uiState.selectedSubject,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = EmeraldAccent,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Dynamic session review list
            Text(
                text = "Full Session Breakdown",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            )

            uiState.quizQuestions.forEachIndexed { qIdx, question ->
                val result = uiState.questionResults.getOrNull(qIdx)
                var expanded by remember { mutableStateOf(false) }

                Card(
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = SlateSurface),
                    border = BorderStroke(1.dp, if (result == true) EmeraldAccent.copy(alpha = 0.5f) else RedAccent.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Question ${qIdx + 1}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (result == true) EmeraldAccent else RedAccent
                            )
                            Icon(
                                imageVector = if (result == true) Icons.Default.Check else Icons.Default.Close,
                                contentDescription = if (result == true) "Correct" else "Wrong",
                                tint = if (result == true) EmeraldAccent else RedAccent,
                                modifier = Modifier.size(16.dp) // Smaller standard icon sizes
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = question.question,
                            fontSize = 14.sp,
                            color = Color.White,
                            maxLines = if (expanded) Int.MAX_VALUE else 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        if (expanded) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Divider(color = SlateBorder)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Correct answer: ${question.options.getOrNull(question.correct) ?: ""}",
                                fontWeight = FontWeight.SemiBold,
                                color = EmeraldAccent,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = question.explanation,
                                fontSize = 13.sp,
                                color = TextMuted,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = onReturnDashboard,
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldAccent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("dashboard_button")
        ) {
            Text("Return to Dashboard", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BookmarksScreen(
    bookmarks: List<BookmarkEntity>,
    onDeleteBookmark: (String) -> Unit
) {
    if (bookmarks.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "No Saved Bookmarks",
                    tint = TextMuted,
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No Saved Bookmarks",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Toggle the bookmark button inside quiz sessions to save key problems for review here.",
                    fontSize = 14.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(bookmarks) { bookmark ->
            var expanded by remember { mutableStateOf(false) }

            Card(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("bookmark_card_${bookmark.subject.lowercase()}"),
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                border = BorderStroke(1.dp, SlateBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(BlueAccent.copy(alpha = 0.12f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = bookmark.subject.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = BlueAccent
                            )
                        }

                        IconButton(
                            onClick = { onDeleteBookmark(bookmark.questionText) },
                            modifier = Modifier
                                .size(32.dp)
                                .testTag("delete_bookmark")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Save",
                                tint = RedAccent,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = bookmark.questionText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        lineHeight = 22.sp
                    )

                    if (expanded) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Divider(color = SlateBorder)
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Correct Answer",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = EmeraldAccent
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        val options = bookmark.getOptionsList()
                        val correctText = options.getOrNull(bookmark.correctIndex) ?: "A"
                        Text(
                            text = correctText,
                            fontSize = 14.sp,
                            color = TextMain
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Step Solution",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = BlueAccent
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = bookmark.explanation,
                            fontSize = 14.sp,
                            color = TextMuted,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryScreen(
    historyList: List<QuizHistoryEntity>,
    onClearHistory: () -> Unit
) {
    if (historyList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "No Practiced History",
                    tint = TextMuted,
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No Practiced History",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Historical performance graphs of your subject drills will show up here after completing quizzes.",
                    fontSize = 14.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }
        }
        return
    }

    // Cumulative stats
    val totalQuestions = historyList.sumOf { it.totalCount }
    val totalScore = historyList.sumOf { it.score }
    val avgAccuracy = if (totalQuestions > 0) (totalScore * 100) / totalQuestions else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Average score stats overview card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SlateSurface),
            border = BorderStroke(1.dp, SlateBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "OVERALL ACCURACY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$avgAccuracy%",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = EmeraldAccent
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "TOTAL DRILLS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${historyList.size} sessions",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Session Logs",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            TextButton(
                onClick = onClearHistory,
                colors = ButtonDefaults.textButtonColors(contentColor = RedAccent),
                modifier = Modifier.testTag("clear_history_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Clear History",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("Clear All")
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(historyList) { log ->
                val accuracyRate = if (log.totalCount > 0) (log.score * 100) / log.totalCount else 0
                val dateStr = try {
                    val sdf = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
                    sdf.format(Date(log.timestamp))
                } catch (e: Exception) {
                    log.timestamp.toString()
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SlateSurface),
                    border = BorderStroke(1.dp, SlateBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = log.subject,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = dateStr,
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${log.score}/${log.totalCount}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = if (accuracyRate >= 80) EmeraldAccent else if (accuracyRate >= 50) BlueAccent else RedAccent
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "$accuracyRate% Accuracy",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}

fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", m, s)
}
