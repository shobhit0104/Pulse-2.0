package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.DashboardTab
import com.example.model.Screen
import com.example.viewmodel.PulseViewModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches Pulse`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Pulse", appName)
    }

    @Test
    fun `test initial state for demo user Ananya`() {
        val viewModel = PulseViewModel()
        val state = viewModel.uiState.value

        assertEquals(Screen.REGISTRATION, state.currentScreen)
        assertEquals("Ananya", state.userProfile.fullName)
        assertEquals(24, state.userProfile.age)
        assertEquals("Bengaluru", state.userProfile.city)
        assertEquals("PCOS", state.userProfile.chronicCondition)
        assertEquals(78, state.userProfile.pulseScore)
        assertEquals(17, state.userProfile.streakDays)
    }

    @Test
    fun `test registration and otp progression`() {
        val viewModel = PulseViewModel()
        viewModel.onRegisterClicked()
        assertEquals(Screen.OTP_VERIFICATION, viewModel.uiState.value.currentScreen)

        viewModel.onVerifyOtpClicked()
        assertEquals(Screen.BUILD_PROFILE_INTRO, viewModel.uiState.value.currentScreen)

        viewModel.onStartAssessmentClicked()
        assertEquals(Screen.ASSESSMENT, viewModel.uiState.value.currentScreen)
        assertEquals(0, viewModel.uiState.value.assessmentState.currentQuestionIndex)
    }

    @Test
    fun `test question progression and submission`() {
        val viewModel = PulseViewModel()
        viewModel.jumpToScreen(Screen.ASSESSMENT)

        // Advance through questions
        repeat(9) {
            viewModel.onAssessmentNext()
        }
        assertEquals(9, viewModel.uiState.value.assessmentState.currentQuestionIndex)

        // Previous
        viewModel.onAssessmentBack()
        assertEquals(8, viewModel.uiState.value.assessmentState.currentQuestionIndex)
    }

    @Test
    fun `test daily pulse completion updates habits and streak`() {
        val viewModel = PulseViewModel()
        viewModel.jumpToScreen(Screen.MAIN_DASHBOARD, DashboardTab.PULSE_60)

        assertFalse(viewModel.uiState.value.isTodayPulseCompleted)

        viewModel.setMovementMinutes("30–60 min")
        viewModel.setMealsPlanned("Yes")
        viewModel.setWellbeingMood("😄 Great")
        viewModel.submitTodayPulse()

        assertTrue(viewModel.uiState.value.isTodayPulseCompleted)
        assertEquals(17, viewModel.uiState.value.userProfile.streakDays)
    }

    @Test
    fun `test doctor consultation request and modals`() {
        val viewModel = PulseViewModel()
        viewModel.bookConsultation("Dr. Priya Sharma")

        assertTrue(viewModel.uiState.value.showConsultationSuccessDialog)
        assertEquals("Dr. Priya Sharma", viewModel.uiState.value.bookedDoctorName)

        viewModel.dismissConsultationSuccess()
        assertFalse(viewModel.uiState.value.showConsultationSuccessDialog)
    }
}
