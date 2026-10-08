package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PulseUiState(
    val currentScreen: Screen = Screen.REGISTRATION,
    val currentTab: DashboardTab = DashboardTab.HOME,
    val userProfile: UserProfile = UserProfile(),
    val assessmentState: AssessmentState = AssessmentState(),
    val isAssessing: Boolean = false,
    val isGeneratingProfile: Boolean = false,
    val generationProgress: Float = 0f,
    val todayPulseInput: TodayPulseInput = TodayPulseInput(),
    val isTodayPulseCompleted: Boolean = false,
    val showTodayPulseCelebration: Boolean = false,
    val selectedDoctorForModal: Doctor? = null,
    val showConsultationSuccessDialog: Boolean = false,
    val bookedDoctorName: String? = null,
    val showDoctorSummaryModal: Boolean = false,
    val showShareSummarySuccess: Boolean = false,
    val showReportUploadModal: Boolean = false,
    val showReportUploadSuccess: Boolean = false,
    val uploadedReportType: String? = null,
    val showPrivacyModal: Boolean = false,
    val showSafetyAlertModal: Boolean = false,
    val showMockUploadDialog: Boolean = false,
    val mockUploadTitle: String = "",
    val mockUploadDescription: String = "",
    val habitHistory: List<DayHabitRecord> = defaultHabitHistory(),
    val timelineList: List<TimelineItem> = defaultTimeline(),
    val registrationNameInput: String = "Ananya",
    val registrationAgeInput: String = "24",
    val registrationCityInput: String = "Bengaluru",
    val registrationPhoneInput: String = "+91 98765 43210",
    val otpInput: String = "849201"
)

fun defaultHabitHistory(): List<DayHabitRecord> = listOf(
    DayHabitRecord("Monday", "M", movementCompleted = true, mealsCompleted = true, wellbeingCompleted = true),
    DayHabitRecord("Tuesday", "T", movementCompleted = true, mealsCompleted = true, wellbeingCompleted = false),
    DayHabitRecord("Wednesday", "W", movementCompleted = true, mealsCompleted = false, wellbeingCompleted = true),
    DayHabitRecord("Thursday", "T", movementCompleted = true, mealsCompleted = true, wellbeingCompleted = true),
    DayHabitRecord("Friday", "F", movementCompleted = false, mealsCompleted = true, wellbeingCompleted = true),
    DayHabitRecord("Saturday", "S", movementCompleted = true, mealsCompleted = true, wellbeingCompleted = true),
    DayHabitRecord("Sunday", "S", movementCompleted = true, mealsCompleted = true, wellbeingCompleted = true)
)

fun defaultTimeline(): List<TimelineItem> = listOf(
    TimelineItem("Today", "Daily Pulse completed", "Movement, Meals & Wellbeing logged", "Pulse 60"),
    TimelineItem("Yesterday", "Sleep recorded", "7.1 hours · Restorative duration", "Habit"),
    TimelineItem("Oct 5", "Blood pressure recorded", "128 / 82 mmHg · Normal resting", "Vitals"),
    TimelineItem("Oct 1", "Health report uploaded", "Routine CBC & Lipid panel", "Records"),
    TimelineItem("Sep 28", "Pulse Profile created", "Baseline assessment completed (72/100)", "Milestone")
)

val DoctorPriyaSharma = Doctor(
    id = "doc_priya",
    name = "Dr. Priya Sharma",
    specialty = "Endocrinologist",
    rating = 4.8,
    experience = "12+ years experience",
    recommendationReason = "Relevant to your selected health condition"
)

val DoctorRahulMehta = Doctor(
    id = "doc_rahul",
    name = "Dr. Rahul Mehta",
    specialty = "General Physician",
    rating = 4.7,
    experience = "10+ years experience",
    recommendationReason = "Good for overall chronic health management"
)

val PulseDimensions = listOf(
    HealthDimension("Sleep", 67, "Room to improve", DimensionTone.NEUTRAL),
    HealthDimension("Activity", 78, "Good", DimensionTone.POSITIVE),
    HealthDimension("Nutrition", 70, "Good foundation", DimensionTone.POSITIVE),
    HealthDimension("Stress & Wellbeing", 58, "Worth paying attention to", DimensionTone.ATTENTION),
    HealthDimension("Health Monitoring", 82, "Strong awareness", DimensionTone.POSITIVE)
)

val PulseChangeReasons = listOf(
    PulseChangeReason("Sleep", "↑ 14%", true, "Your average sleep increased from 5.8h to 6.7h."),
    PulseChangeReason("Activity", "↑ 18%", true, "You were active on 6 of the last 7 days."),
    PulseChangeReason("Stress & Wellbeing", "↑ 9%", true, "Your reported stress improved this week."),
    PulseChangeReason("Nutrition", "↓ 4%", false, "Meal consistency dropped slightly.")
)

class PulseViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PulseUiState())
    val uiState: StateFlow<PulseUiState> = _uiState.asStateFlow()

    fun updateRegistrationName(name: String) {
        _uiState.update { it.copy(registrationNameInput = name) }
    }

    fun updateRegistrationAge(age: String) {
        _uiState.update { it.copy(registrationAgeInput = age) }
    }

    fun updateRegistrationCity(city: String) {
        _uiState.update { it.copy(registrationCityInput = city) }
    }

    fun updateRegistrationPhone(phone: String) {
        _uiState.update { it.copy(registrationPhoneInput = phone) }
    }

    fun updateOtp(otp: String) {
        _uiState.update { it.copy(otpInput = otp) }
    }

    fun onRegisterClicked() {
        _uiState.update { it.copy(currentScreen = Screen.OTP_VERIFICATION) }
    }

    fun onVerifyOtpClicked() {
        _uiState.update {
            it.copy(
                currentScreen = Screen.BUILD_PROFILE_INTRO,
                userProfile = it.userProfile.copy(
                    fullName = it.registrationNameInput.ifBlank { "Ananya" },
                    age = it.registrationAgeInput.toIntOrNull() ?: 24,
                    city = it.registrationCityInput.ifBlank { "Bengaluru" },
                    phoneNumber = it.registrationPhoneInput.ifBlank { "+91 98765 43210" }
                )
            )
        }
    }

    fun onStartAssessmentClicked() {
        _uiState.update {
            it.copy(
                currentScreen = Screen.ASSESSMENT,
                assessmentState = it.assessmentState.copy(currentQuestionIndex = 0)
            )
        }
    }

    fun setQ1HealthGeneral(value: String) {
        _uiState.update { it.copy(assessmentState = it.assessmentState.copy(q1HealthGeneral = value)) }
    }

    fun setQ2HasChronic(value: String) {
        _uiState.update { it.copy(assessmentState = it.assessmentState.copy(q2HasChronic = value)) }
    }

    fun toggleQ2Condition(condition: String) {
        _uiState.update { state ->
            val current = state.assessmentState.q2SelectedConditions
            val updated = if (current.contains(condition)) {
                if (current.size > 1) current - condition else current
            } else {
                current + condition
            }
            state.copy(assessmentState = state.assessmentState.copy(q2SelectedConditions = updated))
        }
    }

    fun setQ3Medication(value: String) {
        _uiState.update { it.copy(assessmentState = it.assessmentState.copy(q3Medication = value)) }
    }

    fun setQ4NoticedChange(value: String) {
        _uiState.update { it.copy(assessmentState = it.assessmentState.copy(q4NoticedChange = value)) }
    }

    fun toggleQ4ChangeChip(chip: String) {
        _uiState.update { state ->
            val current = state.assessmentState.q4ChangeChips
            val updated = if (current.contains(chip)) current - chip else current + chip
            state.copy(assessmentState = state.assessmentState.copy(q4ChangeChips = updated))
        }
    }

    fun setQ5SleepQuality(value: String) {
        _uiState.update { it.copy(assessmentState = it.assessmentState.copy(q5SleepQuality = value)) }
    }

    fun setQ5SleepDuration(value: String) {
        _uiState.update { it.copy(assessmentState = it.assessmentState.copy(q5SleepDuration = value)) }
    }

    fun setQ6PhysicalActivity(value: String) {
        _uiState.update { it.copy(assessmentState = it.assessmentState.copy(q6PhysicalActivity = value)) }
    }

    fun setQ7DietHabits(value: String) {
        _uiState.update { it.copy(assessmentState = it.assessmentState.copy(q7DietHabits = value)) }
    }

    fun setQ8StressFrequency(value: String) {
        _uiState.update { it.copy(assessmentState = it.assessmentState.copy(q8StressFrequency = value)) }
    }

    fun setQ9MonitoringHabit(value: String) {
        _uiState.update { it.copy(assessmentState = it.assessmentState.copy(q9MonitoringHabit = value)) }
    }

    fun toggleQ10Priority(priority: String) {
        _uiState.update { state ->
            val current = state.assessmentState.q10Priorities
            val updated = when {
                current.contains(priority) -> current - priority
                current.size < 2 -> current + priority
                else -> current // max 2
            }
            state.copy(assessmentState = state.assessmentState.copy(q10Priorities = updated))
        }
    }

    fun onAssessmentNext() {
        val currentIndex = _uiState.value.assessmentState.currentQuestionIndex
        if (currentIndex < 9) {
            _uiState.update {
                it.copy(assessmentState = it.assessmentState.copy(currentQuestionIndex = currentIndex + 1))
            }
        } else {
            onSubmitAssessment()
        }
    }

    fun onAssessmentBack() {
        val currentIndex = _uiState.value.assessmentState.currentQuestionIndex
        if (currentIndex > 0) {
            _uiState.update {
                it.copy(assessmentState = it.assessmentState.copy(currentQuestionIndex = currentIndex - 1))
            }
        } else {
            _uiState.update { it.copy(currentScreen = Screen.BUILD_PROFILE_INTRO) }
        }
    }

    fun onSubmitAssessment() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentScreen = Screen.PROFILE_GENERATION,
                    isGeneratingProfile = true,
                    generationProgress = 0.2f
                )
            }
            delay(500)
            _uiState.update { it.copy(generationProgress = 0.55f) }
            delay(600)
            _uiState.update { it.copy(generationProgress = 0.95f) }
            delay(400)
            _uiState.update {
                it.copy(
                    currentScreen = Screen.PULSE_SCORE_RESULT,
                    isGeneratingProfile = false,
                    generationProgress = 1f
                )
            }
        }
    }

    fun onStartPulseJourney() {
        _uiState.update {
            it.copy(
                currentScreen = Screen.MAIN_DASHBOARD,
                currentTab = DashboardTab.HOME
            )
        }
    }

    fun navigateToTab(tab: DashboardTab) {
        _uiState.update {
            it.copy(
                currentScreen = Screen.MAIN_DASHBOARD,
                currentTab = tab
            )
        }
    }

    // Pulse 60 check-in
    fun setMovementMinutes(minutes: String) {
        _uiState.update { it.copy(todayPulseInput = it.todayPulseInput.copy(movementMinutes = minutes)) }
    }

    fun setMealsPlanned(meals: String) {
        _uiState.update { it.copy(todayPulseInput = it.todayPulseInput.copy(mealsPlanned = meals)) }
    }

    fun setWellbeingMood(mood: String) {
        _uiState.update { it.copy(todayPulseInput = it.todayPulseInput.copy(wellbeingMood = mood)) }
    }

    fun submitTodayPulse() {
        _uiState.update { state ->
            val updatedHistory = state.habitHistory.toMutableList()
            // Mark Sunday/Today fully completed
            if (updatedHistory.isNotEmpty()) {
                val lastIndex = updatedHistory.size - 1
                updatedHistory[lastIndex] = updatedHistory[lastIndex].copy(
                    movementCompleted = true,
                    mealsCompleted = true,
                    wellbeingCompleted = true
                )
            }
            val newTimeline = listOf(
                TimelineItem("Just now", "Today's Pulse check-in completed 🎉", "Movement, Meals & Wellbeing logged", "Pulse 60")
            ) + state.timelineList

            state.copy(
                isTodayPulseCompleted = true,
                showTodayPulseCelebration = true,
                habitHistory = updatedHistory,
                timelineList = newTimeline,
                userProfile = state.userProfile.copy(streakDays = 17)
            )
        }
    }

    fun dismissTodayCelebration() {
        _uiState.update { it.copy(showTodayPulseCelebration = false) }
    }

    // Modals & Dialogs
    fun openDoctorModal(doctor: Doctor) {
        _uiState.update { it.copy(selectedDoctorForModal = doctor) }
    }

    fun closeDoctorModal() {
        _uiState.update { it.copy(selectedDoctorForModal = null) }
    }

    fun bookConsultation(doctorName: String) {
        _uiState.update {
            it.copy(
                selectedDoctorForModal = null,
                showConsultationSuccessDialog = true,
                bookedDoctorName = doctorName
            )
        }
    }

    fun dismissConsultationSuccess() {
        _uiState.update { it.copy(showConsultationSuccessDialog = false, bookedDoctorName = null) }
    }

    fun openDoctorSummaryModal() {
        _uiState.update { it.copy(showDoctorSummaryModal = true) }
    }

    fun closeDoctorSummaryModal() {
        _uiState.update { it.copy(showDoctorSummaryModal = false) }
    }

    fun shareDoctorSummary() {
        _uiState.update {
            it.copy(
                showDoctorSummaryModal = false,
                showShareSummarySuccess = true
            )
        }
    }

    fun dismissShareSummarySuccess() {
        _uiState.update { it.copy(showShareSummarySuccess = false) }
    }

    fun openReportUploadModal() {
        _uiState.update { it.copy(showReportUploadModal = true) }
    }

    fun closeReportUploadModal() {
        _uiState.update { it.copy(showReportUploadModal = false) }
    }

    fun submitReportUpload(reportType: String) {
        _uiState.update { state ->
            val newTimelineItem = TimelineItem(
                date = "Today",
                title = "$reportType report uploaded",
                subtitle = "Document secured in your personal timeline",
                tag = "Records"
            )
            state.copy(
                showReportUploadModal = false,
                showReportUploadSuccess = true,
                uploadedReportType = reportType,
                timelineList = listOf(newTimelineItem) + state.timelineList
            )
        }
    }

    fun dismissReportUploadSuccess() {
        _uiState.update { it.copy(showReportUploadSuccess = false, uploadedReportType = null) }
    }

    fun openPrivacyModal() {
        _uiState.update { it.copy(showPrivacyModal = true) }
    }

    fun closePrivacyModal() {
        _uiState.update { it.copy(showPrivacyModal = false) }
    }

    fun openSafetyAlertModal() {
        _uiState.update { it.copy(showSafetyAlertModal = true) }
    }

    fun closeSafetyAlertModal() {
        _uiState.update { it.copy(showSafetyAlertModal = false) }
    }

    fun triggerMockUpload(title: String, description: String) {
        _uiState.update {
            it.copy(
                showMockUploadDialog = true,
                mockUploadTitle = title,
                mockUploadDescription = description
            )
        }
    }

    fun confirmMockUpload() {
        _uiState.update { state ->
            val updatedInput = if (state.mockUploadTitle.contains("meal", ignoreCase = true)) {
                state.todayPulseInput.copy(mealPhotoUploaded = true)
            } else {
                state.todayPulseInput.copy(movementScreenshotUploaded = true)
            }
            state.copy(
                showMockUploadDialog = false,
                todayPulseInput = updatedInput
            )
        }
    }

    fun closeMockUploadDialog() {
        _uiState.update { it.copy(showMockUploadDialog = false) }
    }

    // Hackathon demo jump helper
    fun jumpToScreen(screen: Screen, tab: DashboardTab = DashboardTab.HOME) {
        _uiState.update {
            it.copy(
                currentScreen = screen,
                currentTab = tab
            )
        }
    }

    fun resetDemo() {
        _uiState.value = PulseUiState()
    }
}
