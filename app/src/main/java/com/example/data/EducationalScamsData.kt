package com.example.data

import com.example.model.ScamEducationalScenario

object EducationalScamsData {

    val scenarios = listOf(
        ScamEducationalScenario(
            id = 1,
            titleEn = "Fake Pension Suspension Alert",
            titleTa = "போலி ஓய்வூதியம் நிறுத்தம் எச்சரிக்கை",
            categoryEn = "Pension Department Impersonation",
            categoryTa = "ஓய்வூதியத் துறை ஆள்மாறாட்டம்",
            sampleMessageEn = "URGENT: Dear Pensioner, your monthly pension of Rs. 28,500 has been put on hold due to missing Digital Life Certificate. Click http://pension-update-gov.in/verify to submit details within 24 hours to avoid permanent cancellation.",
            sampleMessageTa = "முக்கிய செய்தி: அன்புள்ள ஓய்வூதியதாரரே, உங்களின் மாதாந்திர ஓய்வூதியம் நிறுத்தி வைக்கப்பட்டுள்ளது. உடனடியாக புதுப்பிக்க http://pension-update-gov.in/verify என்ற இணைப்பை கிளிக் செய்யவும்.",
            warningSignsEn = listOf(
                "Creates panic by claiming pension will be stopped immediately.",
                "Contains a suspicious or unofficial link instead of official portal.",
                "Demands submission within 24 hours.",
                "Threatens permanent cancellation of your hard-earned pension."
            ),
            warningSignsTa = listOf(
                "ஓய்வூதியம் உடனே நிறுத்தப்படும் என அச்சமூட்டுவது.",
                "அரசு இணையதளத்திற்கு பதிலாக போலி லிங்க் கொடுப்பது.",
                "24 மணி நேரத்திற்குள் செய்ய வேண்டும் என அவசரப்படுத்துவது."
            ),
            whatScammerWantsEn = "Your bank account login, Aadhaar number, or debit card PIN so they can siphon out your pension savings.",
            whatScammerWantsTa = "உங்கள் வங்கி கணக்கு விவரங்கள், ஆதார் எண் அல்லது டெபிட் கார்டு PIN-ஐ பெற்று உங்கள் சேமிப்பை திருடுவது.",
            howToProtectEn = listOf(
                "Never click any link in SMS or WhatsApp claiming pension freeze.",
                "Submit Life Certificate (Jeevan Pramaan) only at your authorized bank branch, post office, or official government portal.",
                "Call your pension disbursing bank manager using the number printed on your passbook."
            ),
            howToProtectTa = listOf(
                "SMS அல்லது வாட்ஸ்அப் லிங்குகளை தொடாதீர்கள்.",
                "உங்கள் ஆயுள் சான்றிதழை வங்கி கிளை அல்லது அஞ்சலகத்தில் மட்டுமே சமர்ப்பிக்கவும்.",
                "வங்கி பாஸ்புக்கில் உள்ள எண்ணிற்கு அழைத்து நேரில் விசாரிக்கவும்."
            )
        ),
        ScamEducationalScenario(
            id = 2,
            titleEn = "Bank KYC Update & Account Block",
            titleTa = "வங்கி KYC புதுப்பித்தல் மோசடி",
            categoryEn = "Bank Impersonation",
            categoryTa = "வங்கி ஆள்மாறாட்டம்",
            sampleMessageEn = "Dear SBI customer, your YONO account and Debit Card will be blocked today because your PAN KYC has expired. Update PAN details immediately by clicking: http://bit.ly/sbi-pan-kyc-update",
            sampleMessageTa = "அன்புள்ள வங்கி வாடிக்கையாளரே, உங்களின் KYC காலாவதியாகிவிட்டது. இன்றிரவுக்குள் உங்கள் கணக்கு முடக்கப்படும். புதுப்பிக்க இங்கே கிளிக் செய்க: http://bit.ly/sbi-pan-kyc-update",
            warningSignsEn = listOf(
                "Sent from an ordinary 10-digit mobile number, not official bank sender ID.",
                "Uses URL shorteners like bit.ly or tinyurl.",
                "Threatens that your bank account will be blocked today."
            ),
            warningSignsTa = listOf(
                "சாதாரண செல்போன் எண்ணிலிருந்து குறுஞ்செய்தி வருவது.",
                "bit.ly போன்ற சுருக்கப்பட்ட போலி லிங்குகளை பயன்படுத்துவது.",
                "இன்றே கணக்கு முடக்கப்படும் என்று மிரட்டுவது."
            ),
            whatScammerWantsEn = "Your internet banking username, password, and OTP to transfer your money to their accounts.",
            whatScammerWantsTa = "உங்கள் நெட் பேங்கிங் பாஸ்வேர்ட் மற்றும் OTP-ஐ பெற்று பணத்தை திருடுவது.",
            howToProtectEn = listOf(
                "Banks NEVER ask you to update KYC via an SMS link.",
                "Visit your bank branch directly with your passbook and Aadhaar/PAN copy.",
                "Forward suspicious SMS to 1909 or report to cybercrime."
            ),
            howToProtectTa = listOf(
                "வங்கி ஒருபோதும் SMS லிங்க் மூலம் KYC கேட்காது.",
                "நேரடியாக வங்கி கிளைக்கு சென்று மேலாளரிடம் பேசவும்.",
                "எந்த பாஸ்வேர்டையும் இணையத்தில் உள்ளிடாதீர்கள்."
            )
        ),
        ScamEducationalScenario(
            id = 3,
            titleEn = "OTP and PIN Verification Fraud",
            titleTa = "OTP மற்றும் PIN சரிபார்ப்பு மோசடி",
            categoryEn = "Credential Theft",
            categoryTa = "கடவுச்சொல் திருட்டு",
            sampleMessageEn = "Call from 'Bank Officer': 'Sir, a fraudulent transaction of Rs. 45,000 was attempted on your pension account. We are blocking it. Please read the 6-digit OTP just sent to your phone so I can cancel the charge.'",
            sampleMessageTa = "வங்கி அதிகாரி போன்று பேசுபவர்: 'ஐயா, உங்கள் கணக்கிலிருந்து ரூ. 45,000 எடுக்க முயற்சி நடந்தது. அதை ரத்து செய்ய உங்கள் போனுக்கு வந்த 6 இலக்க OTP சொல்லுங்கள்.'",
            warningSignsEn = listOf(
                "Caller is very polite or extremely urgent, pretending to 'protect' you.",
                "They ask you to read out an OTP sent to your SMS.",
                "The SMS text actually says 'OTP for debit / money transfer'."
            ),
            warningSignsTa = listOf(
                "வங்கி அதிகாரி போல நடித்து உங்களுக்கு உதவி செய்வது போல் பேசுவது.",
                "உங்கள் போனுக்கு வந்த OTP-ஐ கேட்கிறார்கள்.",
                "அந்த OTP உங்கள் கணக்கிலிருந்து பணம் எடுக்க அனுப்பப்பட்டது."
            ),
            whatScammerWantsEn = "The OTP gives the scammer the final authorization to withdraw money from your account.",
            whatScammerWantsTa = "OTP கொடுத்தால் உங்கள் கணக்கில் இருக்கும் பணம் உடனடியாக எடுக்கப்பட்டுவிடும்.",
            howToProtectEn = listOf(
                "OTP stands for One Time Password — it is private to YOU alone.",
                "No genuine bank employee or police officer will ever ask for your OTP.",
                "Hang up immediately if anyone asks for an OTP."
            ),
            howToProtectTa = listOf(
                "OTP என்பது உங்கள் ரகசிய கடவுச்சொல். அதை யாரிடமும் சொல்லக்கூடாது.",
                "வங்கி அதிகாரிகள் ஒருபோதும் OTP கேட்க மாட்டார்கள்.",
                "OTP கேட்டால் உடனே அழைப்பை துண்டிக்கவும்."
            )
        ),
        ScamEducationalScenario(
            id = 4,
            titleEn = "Fake Government Pension Scheme",
            titleTa = "போலி அரசு ஓய்வூதிய நலத்திட்டம்",
            categoryEn = "Welfare Scam",
            categoryTa = "அரசு நலத்திட்ட மோசடி",
            sampleMessageEn = "Prime Minister Senior Citizen Special Relief Scheme: All retired pensioners are entitled to receive Rs. 15,000 monthly extra bonus. Fill application at http://pm-senior-yojana.club and pay Rs. 350 registration charge.",
            sampleMessageTa = "முதியோர் ஓய்வூதியதாரர்களுக்கு சிறப்பு நலத்திட்டம்: கூடுதல் உதவித்தொகை ரூ. 15,000 பெற http://pm-senior-yojana.club இல் பதிவு செய்து ரூ. 350 கட்டணம் செலுத்தவும்.",
            warningSignsEn = listOf(
                "Offers 'free' money or bonuses that seem too good to be true.",
                "Asks for an upfront 'registration fee' or 'processing charge'.",
                "Uses unofficial domain extensions like .club, .xyz, or .top."
            ),
            warningSignsTa = listOf(
                "இலவச பணம் அல்லது கூடுதல் போனஸ் தருவதாக ஆசை காட்டுவது.",
                "முன்பணமாக பதிவு கட்டணம் செலுத்த சொல்வது.",
                ".club, .xyz போன்ற போலி இணையதள முகவரிகள்."
            ),
            whatScammerWantsEn = "To steal the registration fee and capture your debit card details on their fake payment page.",
            whatScammerWantsTa = "பதிவு கட்டணத்தை அபகரிப்பது மற்றும் உங்கள் கார்டு தகவல்களை திருடுவது.",
            howToProtectEn = listOf(
                "All genuine government schemes are announced on official .gov.in or .nic.in portals.",
                "Government welfare schemes NEVER charge an upfront fee through third-party websites.",
                "Consult the local pension office or treasury office directly."
            ),
            howToProtectTa = listOf(
                "அரசு திட்டங்கள் அனைத்தும் .gov.in தளங்களில் மட்டுமே வெளியாகும்.",
                "அரசு எந்த திட்டத்திற்கும் முன்பணம் கேட்காது.",
                "உங்கள் மாவட்ட கருவூல அலுவலகத்தில் விசாரிக்கவும்."
            )
        ),
        ScamEducationalScenario(
            id = 5,
            titleEn = "WhatsApp Impersonation ('Hi Dad/Grandpa')",
            titleTa = "வாட்ஸ்அப் உறவினர் ஆள்மாறாட்டம்",
            categoryEn = "Social Engineering",
            categoryTa = "குடும்பத்தினர் போன்ற நடிப்பு",
            sampleMessageEn = "Hi Grandpa, I dropped my phone in water and this is my temporary number. I have an emergency at college and need Rs. 12,000 urgently to pay clinic fees. Please UPI it to this shopkeeper right now.",
            sampleMessageTa = "வணக்கம் தாத்தா, எனது போன் பழுதாகிவிட்டது, இது தற்காலிக எண். எனக்கு அவசரமாக மருத்துவமனைக்கு ரூ. 12,000 தேவைப்படுகிறது. உடனடியாக இந்த எண்ணிற்கு UPI செய்யுங்கள்.",
            warningSignsEn = listOf(
                "Message arrives from an unknown number pretending to be a child or grandchild.",
                "Claims an emergency and begs for quick money transfer without calling.",
                "Refuses voice call claiming mic is damaged."
            ),
            warningSignsTa = listOf(
                "அறியாத புதிய எண்ணில் இருந்து பேரன்/மகள் போல் செய்தி வருவது.",
                "அவசர மருத்துவ தேவை என பதற்றத்தை ஏற்படுத்துவது.",
                "குரல் அழைப்பில் பேச மறுப்பது."
            ),
            whatScammerWantsEn = "To exploit family love and anxiety to make you transfer money instantly before verifying.",
            whatScammerWantsTa = "குடும்ப பாசத்தை பயன்படுத்தி உண்மையை அறியும் முன் பணத்தை பறிப்பது.",
            howToProtectEn = listOf(
                "Always call your child or grandchild on their EXISTING, known phone number first.",
                "Ask a personal question only your real family member would know.",
                "Never send UPI or bank transfer to an unknown account."
            ),
            howToProtectTa = listOf(
                "உடனடியாக உங்கள் பிள்ளையின் பழைய தெரிந்த எண்ணிற்கு அழைத்து பேசவும்.",
                "குடும்பத்திற்கு மட்டுமே தெரிந்த கேள்வியை கேட்டு உறுதிப்படுத்தவும்.",
                "அறியாத UPI முகவரிக்கு பணம் அனுப்பாதீர்கள்."
            )
        ),
        ScamEducationalScenario(
            id = 6,
            titleEn = "Fraudulent Bank Customer Care Calls",
            titleTa = "போலி வாடிக்கையாளர் சேவை அழைப்பு",
            categoryEn = "Search Engine Fake Numbers",
            categoryTa = "போலி உதவி எண்கள்",
            sampleMessageEn = "Pensioner searches Google for 'Bank Pension Helpline' and calls the top number. Fraudster answers: 'Pension Support Desk. To check your status, please confirm your 16-digit ATM card number and expiry date.'",
            sampleMessageTa = "கூகுளில் தேடிய எண்ணிற்கு அழைக்கும்போது: 'நாங்கள் வங்கி வாடிக்கையாளர் சேவை மையத்திலிருந்து பேசுகிறோம். உங்கள் ஓய்வூதியத்தை அறிய ATM கார்டு 16 இலக்க எண் மற்றும் CVV சொல்லுங்கள்.'",
            warningSignsEn = listOf(
                "Scammers post fake phone numbers on Google Maps and search results.",
                "Representative asks for ATM card numbers, CVV, or PIN.",
                "They ask you to do a test transaction of Rs. 10 or Rs. 100."
            ),
            warningSignsTa = listOf(
                "இணையத்தில் காணப்படும் போலியான ஹெல்ப்லைன் எண்கள்.",
                "ATM கார்டு எண் மற்றும் பின்புறம் உள்ள 3 இலக்க CVV கேட்பது.",
                "பரிசோதனைக்கு ரூ. 10 அனுப்ப சொல்வது."
            ),
            whatScammerWantsEn = "Your full card credentials to make online purchases or debit withdrawals.",
            whatScammerWantsTa = "கார்டு விவரங்களை வைத்து ஆன்லைனில் உங்கள் பணத்தை முழுமையாக எடுப்பது.",
            howToProtectEn = listOf(
                "Never trust customer service numbers found on Google search.",
                "Always look for phone numbers printed on the back of your official Bank Passbook or ATM card.",
                "Customer care NEVER requires your CVV or PIN."
            ),
            howToProtectTa = listOf(
                "கூகுளில் கிடைக்கும் எண்களை கண்மூடித்தனமாக நம்பாதீர்கள்.",
                "உங்கள் வங்கி பாஸ்புக் அல்லது ATM கார்டு பின்புறம் உள்ள எண்ணை மட்டும் அழைக்கவும்.",
                "CVV அல்லது PIN-ஐ எவரிடமும் கூறாதீர்கள்."
            )
        ),
        ScamEducationalScenario(
            id = 7,
            titleEn = "Fake Pension Arrears & Tax Refund",
            titleTa = "போலி ஓய்வூதிய நிலுவைத் தொகை",
            categoryEn = "Refund Scam",
            categoryTa = "நிலுவைத் தொகை மோசடி",
            sampleMessageEn = "Income Tax / Pension Board: You have an approved pension arrear refund of Rs. 64,800 under 7th Pay Commission. To credit amount to your account, deposit refundable processing tax of Rs. 2,400 to account 987654321.",
            sampleMessageTa = "ஓய்வூதிய வாரியம்: உங்களுக்கு ரூ. 64,800 நிலுவைத் தொகை அனுமதிக்கப்பட்டுள்ளது. உங்கள் கணக்கிற்கு வரவு வைக்க ரூ. 2,400 சேவை வரி செலுத்தவும்.",
            warningSignsEn = listOf(
                "Claims you are owed a large sum of unexpected money.",
                "Demands a 'processing fee' or 'advance tax' before giving your money.",
                "Government pensions credit arrears directly to your existing account without fees."
            ),
            warningSignsTa = listOf(
                "எதிர்பாராத பெரிய தொகை உங்களுக்கு வரவுள்ளது என கூறுவது.",
                "பணத்தைப் பெற முதலில் நீங்கள் முன் கட்டணம் செலுத்த வேண்டும் என கேட்பது.",
                "அரசு நிலுவைத் தொகைக்கு எந்த கட்டணமும் கேட்பதில்லை."
            ),
            whatScammerWantsEn = "To trick you into paying the upfront advance fee, which goes directly to the criminal.",
            whatScammerWantsTa = "முன் கட்டணம் என்ற பெயரில் உங்கள் பணத்தை பறிப்பது.",
            howToProtectEn = listOf(
                "Real pension arrears are ALWAYS deposited directly by the treasury into your bank account.",
                "You NEVER have to pay a single rupee to receive legitimate pension arrears.",
                "Check your bank account passbook directly at your branch."
            ),
            howToProtectTa = listOf(
                "உண்மையான நிலுவைத் தொகை நேரிடையாக உங்கள் வங்கி கணக்கில் வரவு வைக்கப்படும்.",
                "ஓய்வூதியத்தை பெற ஒரு பைசா கூட நீங்கள் செலுத்த வேண்டியதில்லை.",
                "வங்கிக்கு சென்று பாஸ்புக்கை பதிவு செய்து சரிபார்க்கவும்."
            )
        ),
        ScamEducationalScenario(
            id = 8,
            titleEn = "Remote Access Application Scam (AnyDesk)",
            titleTa = "ரிமோட் ஆப் மோசடி (AnyDesk / TeamViewer)",
            categoryEn = "Device Takeover",
            categoryTa = "போன் கட்டுப்பாடு திருட்டு",
            sampleMessageEn = "Caller: 'Sir, your mobile banking is not secured. Please open Google Play Store and install AnyDesk or TeamViewer QuickSupport so our engineer can fix it for you.'",
            sampleMessageTa = "அழைப்பாளர்: 'ஐயா, உங்கள் வங்கி ஆப் பாதுகாப்பாக இல்லை. Play Store-ல் AnyDesk அல்லது TeamViewer செயலியை பதிவிறக்கம் செய்து குறியீட்டை சொல்லுங்கள், சரிசெய்கிறோம்.'",
            warningSignsEn = listOf(
                "Caller asks you to install an application from Play Store.",
                "They ask you to share a 9-digit or 10-digit remote access code.",
                "They ask you to keep your phone screen turned on and unlocked."
            ),
            warningSignsTa = listOf(
                "போனில் புதிய செயலியை (App) இன்ஸ்டால் செய்ய சொல்வது.",
                "திரையில் தோன்றும் 9 இலக்க குறியீட்டை கேட்கிறார்கள்.",
                "போன் திரையை அணைக்க வேண்டாம் என கூறுவது."
            ),
            whatScammerWantsEn = "Once installed, the scammer can see your screen, view your OTPs, and control your phone to steal money.",
            whatScammerWantsTa = "இந்த செயலி மூலம் உங்கள் போன் திரையை அவர்களால் பார்க்கவும், இயக்கவும் முடியும்.",
            howToProtectEn = listOf(
                "NEVER install AnyDesk, TeamViewer, RustDesk, or QuickSupport on the request of any caller.",
                "If someone tells you to download an app to 'fix' your pension or bank, hang up immediately.",
                "If already installed, immediately turn off Wi-Fi/Mobile Data and uninstall the app."
            ),
            howToProtectTa = listOf(
                "யார் கூறினாலும் AnyDesk, TeamViewer போன்ற செயலிகளை பதிவிறக்கம் செய்யாதீர்கள்.",
                "அழைப்பை உடனடியாக துண்டிக்கவும்.",
                "ஏற்கனவே செய்திருந்தால் உடனே இணையத்தை ஆஃப் செய்துவிட்டு செயலியை நீக்கவும்."
            )
        )
    )
}
