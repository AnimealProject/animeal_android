package com.epmedu.animeal.profile.domain.model

import androidx.emoji2.text.EmojiCompat
import com.epmedu.animeal.extensions.toIntArray
import java.util.*

enum class Region(val phoneNumberCode: String, val phoneNumberDigitsCount: IntArray) {

    AF("+93", intArrayOf(9)),
    AL("+355", (3..9).toIntArray()),
    DZ("+213", intArrayOf(8, 9)),
    AS("+1684", intArrayOf(7)),
    AD("+376", intArrayOf(6, 8, 9)),
    AO("+244", intArrayOf(9)),
    AI("+1264", intArrayOf(7)),
    AG("+1268", intArrayOf(7)),
    AR("+54", intArrayOf(10)),
    AM("+374", intArrayOf(8)),
    AW("+297", intArrayOf(7)),
    AU("+61", (5..15).toIntArray()),
    AT("+43", (4..13).toIntArray()),
    AZ("+994", intArrayOf(8, 9)),
    BS("+1242", intArrayOf(7)),
    BH("+973", intArrayOf(8)),
    BD("+880", (6..10).toIntArray()),
    BB("+1246", intArrayOf(7)),
    BY("+375", intArrayOf(9)),
    BE("+32", intArrayOf(8, 9)),
    BZ("+501", intArrayOf(7)),
    BJ("+229", intArrayOf(8)),
    BM("+1441", intArrayOf(7)),
    BT("+975", intArrayOf(7, 8)),
    BO("+591", intArrayOf(8)),
    BA("+387", intArrayOf(8)),
    BW("+267", intArrayOf(7, 8)),
    BR("+55", intArrayOf(10)),
    BN("+673", intArrayOf(7)),
    BG("+359", intArrayOf(7, 8, 9)),
    BF("+226", intArrayOf(8)),
    BI("+257", intArrayOf(8)),
    KH("+855", intArrayOf(8)),
    CM("+237", intArrayOf(8)),
    CA("+1", intArrayOf(10)),
    CV("+238", intArrayOf(7)),
    CF("+236", intArrayOf(8)),
    TD("+235", intArrayOf(8)),
    CL("+56", intArrayOf(8, 9)),
    CN("+86", (5..12).toIntArray()),
    CO("+57", intArrayOf(8, 10)),
    KM("+269", intArrayOf(7)),
    CG("+242", intArrayOf(9)),
    CR("+506", intArrayOf(8)),
    HR("+385", (8..12).toIntArray()),
    CU("+53", intArrayOf(6, 7, 8)),
    CY("+357", intArrayOf(8, 11)),
    CZ("+420", (4..12).toIntArray()),
    DK("+45", intArrayOf(8)),
    EC("+593", intArrayOf(8)),
    EG("+20", intArrayOf(7, 8, 9)),
    SV("+503", intArrayOf(7, 8, 11)),
    GQ("+240", intArrayOf(9)),
    ER("+291", intArrayOf(7)),
    EE("+372", (7..10).toIntArray()),
    ET("+251", intArrayOf(9)),
    FJ("+679", intArrayOf(7)),
    FI("+358", (5..12).toIntArray()),
    FR("+33", intArrayOf(9)),
    GF("+594", intArrayOf(9)),
    PF("+689", intArrayOf(6)),
    GA("+241", intArrayOf(6, 7)),
    GM("+220", intArrayOf(7)),
    GE("+995", intArrayOf(9)),
    DE("+49", (6..13).toIntArray()),
    GH("+233", (5..9).toIntArray()),
    GI("+350", intArrayOf(8)),
    GR("+30", intArrayOf(10)),
    GL("+299", intArrayOf(6)),
    GT("+502", intArrayOf(8)),
    GY("+592", intArrayOf(7)),
    HT("+509", intArrayOf(8)),
    VA("+379", intArrayOf(8)),
    HN("+504", intArrayOf(8)),
    HK("+852", intArrayOf(4, 8, 9)),
    HU("+36", intArrayOf(8, 9)),
    IS("+354", intArrayOf(7, 9)),
    IN("+91", (7..10).toIntArray()),
    ID("+62", (5..10).toIntArray()),
    IR("+98", (6..10).toIntArray()),
    IQ("+964", intArrayOf(8, 9, 10)),
    IE("+353", (7..11).toIntArray()),
    IT("+39", intArrayOf(11)),
    JM("+1876", intArrayOf(7)),
    JP("+81", (5..13).toIntArray()),
    JO("+962", (5..9).toIntArray()),
    KZ("+7", intArrayOf(10)),
    KE("+254", (6..10).toIntArray()),
    KR("+82", (8..11).toIntArray()),
    KW("+965", intArrayOf(7, 8)),
    KG("+996", intArrayOf(9)),
    LV("+371", intArrayOf(7, 8)),
    LB("+961", intArrayOf(7, 8)),
    LS("+266", intArrayOf(8)),
    LR("+231", intArrayOf(7, 8)),
    LI("+423", intArrayOf(7, 8, 9)),
    LT("+370", intArrayOf(8)),
    LU("+352", (4..11).toIntArray()),
    MO("+853", intArrayOf(7, 8)),
    MY("+60", intArrayOf(7, 8, 9)),
    ML("+223", intArrayOf(8)),
    MT("+356", intArrayOf(8)),
    MU("+230", intArrayOf(7)),
    MX("+52", intArrayOf(10)),
    FM("+691", intArrayOf(7)),
    MD("+373", intArrayOf(8)),
    MC("+377", (5..9).toIntArray()),
    MN("+976", intArrayOf(7, 8)),
    ME("+382", (4..12).toIntArray()),
    MS("+1664", intArrayOf(7)),
    MA("+212", intArrayOf(9)),
    MZ("+258", intArrayOf(8, 9)),
    NP("+977", intArrayOf(8, 9)),
    NL("+31", intArrayOf(9)),
    NC("+687", intArrayOf(6)),
    NZ("+64", (3..10).toIntArray()),
    NI("+505", intArrayOf(8)),
    NU("+683", intArrayOf(4)),
    NO("+47", intArrayOf(5, 8)),
    OM("+968", intArrayOf(7, 8)),
    PK("+92", (8..11).toIntArray()),
    PW("+680", intArrayOf(7)),
    PA("+507", intArrayOf(7, 8)),
    PG("+675", (4..11).toIntArray()),
    PY("+595", (5..9).toIntArray()),
    PE("+51", (8..11).toIntArray()),
    PH("+63", (8..10).toIntArray()),
    PL("+48", (6..9).toIntArray()),
    PT("+351", intArrayOf(9, 11)),
    PR("+1939", intArrayOf(7)),
    QA("+974", (3..8).toIntArray()),
    RO("+40", intArrayOf(9)),
    RW("+250", intArrayOf(9)),
    WS("+685", (3..7).toIntArray()),
    SM("+378", (6..10).toIntArray()),
    SA("+966", intArrayOf(8, 9)),
    SN("+221", intArrayOf(9)),
    RS("+381", (4..12).toIntArray()),
    SC("+248", intArrayOf(7)),
    SL("+232", intArrayOf(8)),
    SG("+65", (8..12).toIntArray()),
    SK("+421", (4..9).toIntArray()),
    SI("+386", intArrayOf(8)),
    SO("+252", (5..8).toIntArray()),
    ZA("+27", intArrayOf(9)),
    ES("+34", intArrayOf(9)),
    LK("+94", intArrayOf(9)),
    SD("+249", intArrayOf(9)),
    SR("+597", intArrayOf(6, 7)),
    SZ("+268", intArrayOf(7, 8)),
    SE("+46", (7..13).toIntArray()),
    CH("+41", (4..12).toIntArray()),
    TW("+886", intArrayOf(8, 9)),
    TJ("+992", intArrayOf(9)),
    TZ("+255", intArrayOf(9)),
    TH("+66", intArrayOf(8, 9)),
    TL("+670", intArrayOf(7)),
    TG("+228", intArrayOf(8)),
    TK("+690", intArrayOf(4)),
    TO("+676", intArrayOf(5, 7)),
    TT("+1868", intArrayOf(7)),
    TN("+216", intArrayOf(8)),
    TR("+90", intArrayOf(10)),
    TM("+993", intArrayOf(8)),
    TV("+688", intArrayOf(5, 6)),
    UG("+256", intArrayOf(9)),
    UA("+380", intArrayOf(9)),
    AE("+971", intArrayOf(8, 9)),
    GB("+44", (7..10).toIntArray()),
    US("+1", intArrayOf(10)),
    UY("+598", (4..11).toIntArray()),
    UZ("+998", intArrayOf(9)),
    VU("+678", intArrayOf(5, 7)),
    VE("+58", intArrayOf(10)),
    VN("+84", (7..10).toIntArray()),
    WF("+681", intArrayOf(6)),
    YE("+967", (6..9).toIntArray()),
    ZM("+260", intArrayOf(9)),
    ZW("+263", (5..10).toIntArray()),
}

fun Region.flagEmoji(): String {
    val flagOffset = 0x1F1E6
    val asciiOffset = 0x41

    val country = this.name

    val firstChar = Character.codePointAt(
        country,
        0
    ) - asciiOffset + flagOffset
    val secondChar = Character.codePointAt(
        country,
        1
    ) - asciiOffset + flagOffset

    return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
}

fun Region.countryName(): String {
    val loc = Locale(
        "en",
        this.name
    )
    return loc.displayCountry
}

fun Region.getFormat(): String {
    return if (this == Region.GE) {
        "xxx xx-xx-xx"
    } else {
        val digits = phoneNumberDigitsCount.last()
        (1..digits).joinToString("") { "x" }
    }
}

fun Region.codesListText(): String {
    val emojiCompat = EmojiCompat.get()
    val flag = when (emojiCompat.loadState) {
        EmojiCompat.LOAD_STATE_SUCCEEDED -> emojiCompat.process(flagEmoji())
        else -> flagEmoji()
    }
    return "$flag $phoneNumberCode ${countryName()}"
}