# Senior Shield – AI Scam Detector for Elderly Pensioners

Senior Shield is an accessible, high-contrast native Android application built with Jetpack Compose and Gemini AI to protect elderly pensioners from financial fraud, fake pension notices, fraudulent WhatsApp/SMS messages, and scam phone calls.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The following key decisions have been aligned based on your selections and the Android execution environment:

- **Confirmed Decision 1 (Platform & UI Framework)**: Built as a native Android application using **Jetpack Compose (Material 3)**. This enables direct hardware integration for Android Text-to-Speech (`TextToSpeech`), voice dictation (`SpeechRecognizer`), accessible font scaling, and direct APK generation and browser preview.
- **Confirmed Decision 2 (Scam Detection Engine)**: **Hybrid Detection Architecture**. Combines a smart on-device heuristic engine (detecting pension freeze threats, urgent money demands, fake KYC, Aadhaar/OTP harvesting, and malicious link patterns) with **Gemini 3.5 Flash AI** via direct REST integration. This guarantees the app works instantly out-of-the-box in demo/offline mode while offering in-depth AI reasoning when an API key is present.
- **Confirmed Decision 3 (Language & Accessibility)**: **English default with instant one-tap toggle for Tamil (தமிழ்)**. All warnings, scam categories, emergency guidance, educational lessons, and voice read-aloud buttons support both English and Tamil. Touch targets are enlarged (minimum 56dp) with high-contrast calm guardian blue and prominent warning badges.

---

## 1. Overview & Core Concept

- **What It Does**: Allows elderly pensioners to paste, type, or speak suspicious messages (SMS, WhatsApp, fake pension alerts, emails) and evaluate suspicious phone calls/caller statements. The app delivers a crystal-clear risk rating (**LOW RISK**, **SUSPICIOUS**, or **HIGH RISK**), highlights specific red flag phrases, provides simple next steps, reads explanations aloud, and provides instant access to India's National Cybercrime Helpline **1930** and bank fraud protocol.
- **Target Audience / Persona**: Elderly pensioners aged 60+ who may have limited technical experience, eyesight difficulties, or anxiety when receiving threatening calls about suspended pensions or frozen bank accounts.
- **Key Value**: Delivers instant reassurance and unambiguous, jargon-free protection without collecting personal data or storing private messages.

---

## 2. User Experience & Visual Design

### Key User Flows

```
┌────────────────────────────────────────────────────────────────────────┐
│                              HOME SCREEN                               │
│  - Senior Shield Header & Instant Language Toggle (EN / தமிழ்)         │
│  - Daily Safety Tip with Text-to-Speech Read Aloud                     │
│  - Critical Golden Rule Banner: "Never share OTP, PIN, or Password"    │
│  - 4 Large Action Cards: Check Message, Check Call, Learn, Help        │
└───────┬────────────────────┬────────────────────┬──────────────────────┘
        │                    │                    │
        ▼                    ▼                    ▼
┌──────────────────┐ ┌──────────────────┐ ┌──────────────────┐
│  CHECK MESSAGE   │ │    CHECK CALL    │ │  EMERGENCY HELP  │
│ - Text input box │ │ - Phone number   │ │ - Red Alert Card │
│ - Voice dictation│ │ - Common caller  │ │ - 1930 One-Tap   │
│ - 4 Sample Scams │ │   statements     │ │   Dial Button    │
│ - Analyze Button │ │ - Assess Button  │ │ - Bank contact   │
│ - Risk Badge     │ │ - Call Risk Card │ │   safety steps   │
│ - Red flag terms │ │ - What caller    │ │ - Cybercrime     │
│ - TTS Read-Aloud │ │   really wants   │ │   portal link    │
└──────────────────┘ └──────────────────┘ └──────────────────┘
```

### Visual Identity & Theme
- **Color Palette**:
  - Primary / Guardian Blue: `#0D47A1` (Deep Trustworthy Navy Blue)
  - Primary Container / Soft Sky: `#E3F2FD`
  - High Contrast Dark Neutral: `#1A1C1E` for sharp text legibility
  - Low Risk Badge: `#1B5E20` on `#E8F5E9` (Calm Green)
  - Suspicious Badge: `#E65100` on `#FFF3E0` (Warning Amber)
  - High Risk Badge: `#B71C1C` on `#FFEBEE` (Urgent Red)
  - High Touch Surface: White cards with subtle border elevation and rounded corners (`16.dp`).
- **Typography & Accessibility**:
  - Extra-large default font sizes (Title: 24sp bold, Body: 18sp medium, Buttons: 18sp bold).
  - Configurable text size setting (Normal, Large, Extra Large).
  - Touch targets sized to 56dp+ with visible ripple effects and tactile icons.
  - Dedicated speaker button with visual soundwave state for speech playback.

---

## 3. Key Product Decisions & Trade-Offs

- **Hybrid AI Engine with Built-in Offline Fallback**:
  - *Approach*: Use Gemini 3.5 Flash for nuanced analysis when online and configured; instantly fallback to the specialized pension scam heuristic analyzer if offline or if API key is not present.
  - *Why*: Pensioners need reliability. If network drops or an API key has reached quota, the app must still protect them from obvious KYC, OTP, and pension stoppage scams.
- **Full Bi-lingual Architecture (English & Tamil)**:
  - *Approach*: Built-in language repository supplying translations for all UI strings, preset scams, caller prompts, safety advice, and TTS audio cues.
  - *Why*: Many Indian pensioners are more comfortable reading or hearing instructions in their mother tongue (Tamil), especially under stress.
- **Privacy & Security by Design**:
  - *Approach*: Messages and numbers are analyzed purely in-memory and discarded upon exiting or checking another message. Prominent warning advises users never to paste actual passwords or bank credentials into any app.

---

## 4. Technical Architecture & Data Strategy

### Component Hierarchy & Architecture

```
┌────────────────────────────────────────────────────────────────────────┐
│                         SeniorShieldApplication                        │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                    ┌───────────────┴───────────────┐
                    ▼                               ▼
        ┌───────────────────────┐       ┌───────────────────────┐
        │   SpeechManager       │       │    ScamRepository     │
        │ - Android TTS         │       │ - Gemini REST Client  │
        │ - SpeechRecognizer    │       │ - Heuristic Analyzer  │
        └───────────┬───────────┘       │ - Educational Samples │
                    │                   └───────────┬───────────┘
                    └───────────────┬───────────────┘
                                    ▼
                        ┌───────────────────────┐
                        │   SeniorShieldViewModel
                        │ - UiState Flow        │
                        │ - Language State      │
                        │ - Text Size State     │
                        │ - Analysis States     │
                        └───────────┬───────────┘
                                    │
        ┌───────────────────────────┼───────────────────────────┐
        ▼                           ▼                           ▼
┌──────────────┐            ┌──────────────┐            ┌──────────────┐
│  HomeScreen  │            │ CheckMessage │            │ CheckCall    │
└──────────────┘            └──────────────┘            └──────────────┘
        │                           │                           │
        ▼                           ▼                           ▼
┌──────────────┐            ┌──────────────┐
│  LearnScreen │            │  HelpScreen  │
└──────────────┘            └──────────────┘
```

### Core Entities & State

- `ScamRiskLevel`: `LOW_RISK` (Green), `SUSPICIOUS` (Amber), `HIGH_RISK` (Red), `UNKNOWN`.
- `AnalysisResult`: Risk level, headline, simple explanation, flagged keywords, actionable advice, and timestamp.
- `CallerAssessment`: Common caller prompt tags, flagged deception patterns, confidence, and recommended counter-actions.
- `ScamEducationalItem`: 8 realistic scam scenarios (Pension Suspension, KYC Expiry, Electricity Bill Cut, Aadhaar Biometric Lock, WhatsApp Grandchild Impersonation, Lottery Prize, Customer Care Remote Access, Fake Arrears).
- `Language`: `ENGLISH` or `TAMIL`.

---

## 5. Implementation Steps

1. **Platform Metadata & Resources**: Set app name to "Senior Shield" in `metadata.json` and `strings.xml`. Add Internet and Voice permissions to `AndroidManifest.xml`.
2. **Environment & Secrets**: Uncomment `GEMINI_API_KEY` in `.env.example` to enable Gemini AI integration.
3. **Data & Repository Layer**:
   - `ScamAnalysisModels.kt`: Data models for scam evaluations, educational guides, and localized copy.
   - `GeminiApiService.kt`: REST client targeting Gemini 3.5 Flash with structured scam prompt analysis.
   - `ScamDetectorEngine.kt`: Hybrid detection orchestrator (Gemini AI + deep heuristic rulebook for pension fraud patterns).
   - `LocalizationHelper.kt`: Complete English and Tamil translations.
4. **Hardware & Accessibility Layer**:
   - `SpeechAssistant.kt`: Wrapper around Android `TextToSpeech` and `SpeechRecognizer` with permission handling and graceful fallbacks.
5. **UI Layer (Jetpack Compose & M3)**:
   - `SeniorShieldTheme.kt`: M3 color scheme, accessible typography tokens, and high contrast styling.
   - `Components.kt`: Large action buttons, Speak button with animation, Risk Badge cards, Language selector.
   - `HomeScreen.kt`: Hero Golden Rule, Tip of the Day, TTS speak button, 4 large action cards.
   - `CheckMessageScreen.kt`: Input box, voice dictation, quick sample chips, hybrid AI analysis, detailed results card.
   - `CheckCallScreen.kt`: Caller number check, common statement selector, deception analysis, refusal script.
   - `LearnScamsScreen.kt`: 8 interactive educational cards with "What Scammer Wants" and "How to Protect".
   - `EmergencyHelpScreen.kt`: SOS card, one-tap dial 1930, bank contact instructions, cybercrime portal launcher.
   - `SettingsScreen.kt`: Language switch, font size modifier, speech test, privacy promise.
6. **Testing & Verification**:
   - Local unit tests in `ScamDetectorEngineTest.kt` verifying scam pattern classifications (pension freeze, OTP theft, low risk messages).
   - Build verification using `compile_applet`.
