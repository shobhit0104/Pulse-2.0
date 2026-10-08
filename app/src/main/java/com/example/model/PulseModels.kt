package com.example.model

enum class Screen {
    REGISTRATION,
    OTP_VERIFICATION,
    BUILD_PROFILE_INTRO,
    ASSESSMENT,
    PROFILE_GENERATION,
    PULSE_SCORE_RESULT,
    MAIN_DASHBOARD
}

enum class DashboardTab(val label: String) {
    HOME("Home"),
    PULSE_60("Pulse 60"),
    TRACK("Track"),
    PROFILE("Profile"),
    SETTINGS("Settings")
}

data class UserProfile(
    val fullName: String = "Ananya",
    val age: Int = 24,
    val gender: String = "Female",
    val state: String = "Karnataka",
    val city: String = "Bengaluru",
    val phoneNumber: String = "+91 98765 43210",
    val email: String = "ananya@example.com",
    val chronicCondition: String = "PCOS",
    val priorities: List<String> = listOf("Sleep", "Stress / mental wellbeing"),
    val pulseScore: Int = 78,
    val previousPulseScore: Int = 72,
    val scoreDelta: Int = 6,
    val streakDays: Int = 17,
    val pulse60Day: Int = 17,
    val avgSleepHours: Double = 6.7,
    val avgActivityMins: Int = 32,
    val avgStressScore: Double = 5.1,
    val medicationAdherence: Int = 89,
    val recentBloodPressure: String = "128 / 82"
)

data class HealthDimension(
    val name: String,
    val score: Int,
    val status: String,
    val statusTone: DimensionTone = DimensionTone.POSITIVE
)

enum class DimensionTone {
    POSITIVE,
    NEUTRAL,
    ATTENTION
}

data class DayHabitRecord(
    val dayName: String,
    val dayShort: String,
    val movementCompleted: Boolean,
    val mealsCompleted: Boolean,
    val wellbeingCompleted: Boolean
) {
    val isFullyCompleted: Boolean
        get() = movementCompleted && mealsCompleted && wellbeingCompleted
    val isPartiallyCompleted: Boolean
        get() = (movementCompleted || mealsCompleted || wellbeingCompleted) && !isFullyCompleted
    val isMissed: Boolean
        get() = !movementCompleted && !mealsCompleted && !wellbeingCompleted
}

data class TimelineItem(
    val date: String,
    val title: String,
    val subtitle: String? = null,
    val tag: String? = null
)

data class Doctor(
    val id: String,
    val name: String,
    val specialty: String,
    val rating: Double,
    val experience: String,
    val recommendationReason: String,
    val hospitalAffiliation: String = "Bengaluru Health Collective"
)

data class PulseChangeReason(
    val metric: String,
    val changeText: String,
    val isPositive: Boolean,
    val detail: String
)

data class AssessmentState(
    val currentQuestionIndex: Int = 0,
    val q1HealthGeneral: String = "Okay",
    val q2HasChronic: String = "Yes — one condition",
    val q2SelectedConditions: Set<String> = setOf("PCOS"),
    val q3Medication: String = "Yes, and I follow it regularly",
    val q4NoticedChange: String = "A small change",
    val q4ChangeChips: Set<String> = setOf("Sleep", "Energy"),
    val q5SleepQuality: String = "Sometimes disturbed",
    val q5SleepDuration: String = "6–7 hours",
    val q6PhysicalActivity: String = "Lightly active",
    val q7DietHabits: String = "Mixed",
    val q8StressFrequency: String = "Often",
    val q9MonitoringHabit: String = "I regularly monitor my health",
    val q10Priorities: Set<String> = setOf("Sleep", "Stress / mental wellbeing")
)

data class TodayPulseInput(
    val movementMinutes: String = "15–30 min",
    val movementScreenshotUploaded: Boolean = false,
    val mealsPlanned: String = "Yes",
    val mealPhotoUploaded: Boolean = false,
    val wellbeingMood: String = "🙂 Good"
)
