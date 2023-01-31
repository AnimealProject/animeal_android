@file:Suppress(
    "ComplexMethod",
    "LongMethod",
    "ReturnCount"
)

package com.epmedu.animeal.signup.enterphone.presentation

import com.epmedu.animeal.extensions.toIntArray
import java.util.*

enum class Region {
    AF,
    AL,
    DZ,
    AS,
    AD,
    AO,
    AI,
    AG,
    AR,
    AM,
    AW,
    AU,
    AT,
    AZ,
    BS,
    BH,
    BD,
    BB,
    BY,
    BE,
    BZ,
    BJ,
    BM,
    BT,
    BO,
    BA,
    BW,
    BR,
    BN,
    BG,
    BF,
    BI,
    KH,
    CM,
    CA,
    CV,
    CF,
    TD,
    CL,
    CN,
    CO,
    KM,
    CG,
    CR,
    HR,
    CU,
    CY,
    CZ,
    DK,
    EC,
    EG,
    SV,
    GQ,
    ER,
    EE,
    ET,
    FJ,
    FI,
    FR,
    GF,
    PF,
    GA,
    GM,
    GE,
    DE,
    GH,
    GI,
    GR,
    GL,
    GT,
    GY,
    HT,
    VA,
    HN,
    HK,
    HU,
    IS,
    IN,
    ID,
    IR,
    IQ,
    IE,
    IT,
    JM,
    JP,
    JO,
    KZ,
    KE,
    KR,
    KW,
    KG,
    LV,
    LB,
    LS,
    LR,
    LI,
    LT,
    LU,
    MO,
    MY,
    ML,
    MT,
    MU,
    MX,
    FM,
    MD,
    MC,
    MN,
    ME,
    MS,
    MA,
    MZ,
    NP,
    NL,
    NC,
    NZ,
    NI,
    NU,
    NO,
    OM,
    PK,
    PW,
    PA,
    PG,
    PY,
    PE,
    PH,
    PL,
    PT,
    PR,
    QA,
    RO,
    RW,
    WS,
    SM,
    SA,
    SN,
    RS,
    SC,
    SL,
    SG,
    SK,
    SI,
    SO,
    ZA,
    ES,
    LK,
    SD,
    SR,
    SZ,
    SE,
    CH,
    TW,
    TJ,
    TZ,
    TH,
    TL,
    TG,
    TK,
    TO,
    TT,
    TN,
    TR,
    TM,
    TV,
    UG,
    UA,
    AE,
    GB,
    US,
    UY,
    UZ,
    VU,
    VE,
    VN,
    WF,
    YE,
    ZM,
    ZW,
}

fun Region.phoneNumberCode(): String {
    when (this) {
        Region.AF -> return "+93"
        Region.AL -> return "+355"
        Region.DZ -> return "+213"
        Region.AS -> return "+1684"
        Region.AD -> return "+376"
        Region.AO -> return "+244"
        Region.AI -> return "+1264"
        Region.AG -> return "+1268"
        Region.AR -> return "+54"
        Region.AM -> return "+374"
        Region.AW -> return "+297"
        Region.AU -> return "+61"
        Region.AT -> return "+43"
        Region.AZ -> return "+994"
        Region.BS -> return "+1242"
        Region.BH -> return "+973"
        Region.BD -> return "+880"
        Region.BB -> return "+1246"
        Region.BY -> return "+375"
        Region.BE -> return "+32"
        Region.BZ -> return "+501"
        Region.BJ -> return "+229"
        Region.BM -> return "+1441"
        Region.BT -> return "+975"
        Region.BO -> return "+591"
        Region.BA -> return "+387"
        Region.BW -> return "+267"
        Region.BR -> return "+55"
        Region.BN -> return "+673"
        Region.BG -> return "+359"
        Region.BF -> return "+226"
        Region.BI -> return "+257"
        Region.KH -> return "+855"
        Region.CM -> return "+237"
        Region.CA -> return "+1"
        Region.CV -> return "+238"
        Region.CF -> return "+236"
        Region.TD -> return "+235"
        Region.CL -> return "+56"
        Region.CN -> return "+86"
        Region.CO -> return "+57"
        Region.KM -> return "+269"
        Region.CG -> return "+242"
        Region.CR -> return "+506"
        Region.HR -> return "+385"
        Region.CU -> return "+53"
        Region.CY -> return "+357"
        Region.CZ -> return "+420"
        Region.DK -> return "+45"
        Region.EC -> return "+593"
        Region.EG -> return "+20"
        Region.SV -> return "+503"
        Region.GQ -> return "+240"
        Region.ER -> return "+291"
        Region.EE -> return "+372"
        Region.ET -> return "+251"
        Region.FJ -> return "+679"
        Region.FI -> return "+358"
        Region.FR -> return "+33"
        Region.GF -> return "+594"
        Region.PF -> return "+689"
        Region.GA -> return "+241"
        Region.GM -> return "+220"
        Region.GE -> return "+995"
        Region.DE -> return "+49"
        Region.GH -> return "+233"
        Region.GI -> return "+350"
        Region.GR -> return "+30"
        Region.GL -> return "+299"
        Region.GT -> return "+502"
        Region.GY -> return "+592"
        Region.HT -> return "+509"
        Region.VA -> return "+379"
        Region.HN -> return "+504"
        Region.HK -> return "+852"
        Region.HU -> return "+36"
        Region.IS -> return "+354"
        Region.IN -> return "+91"
        Region.ID -> return "+62"
        Region.IR -> return "+98"
        Region.IQ -> return "+964"
        Region.IE -> return "+353"
        Region.IT -> return "+39"
        Region.JM -> return "+1876"
        Region.JP -> return "+81"
        Region.JO -> return "+962"
        Region.KZ -> return "+7"
        Region.KE -> return "+254"
        Region.KR -> return "+82"
        Region.KW -> return "+965"
        Region.KG -> return "+996"
        Region.LV -> return "+371"
        Region.LB -> return "+961"
        Region.LS -> return "+266"
        Region.LR -> return "+231"
        Region.LI -> return "+423"
        Region.LT -> return "+370"
        Region.LU -> return "+352"
        Region.MO -> return "+853"
        Region.MY -> return "+60"
        Region.ML -> return "+223"
        Region.MT -> return "+356"
        Region.MU -> return "+230"
        Region.MX -> return "+52"
        Region.FM -> return "+691"
        Region.MD -> return "+373"
        Region.MC -> return "+377"
        Region.MN -> return "+976"
        Region.ME -> return "+382"
        Region.MS -> return "+1664"
        Region.MA -> return "+212"
        Region.MZ -> return "+258"
        Region.NP -> return "+977"
        Region.NL -> return "+31"
        Region.NC -> return "+687"
        Region.NZ -> return "+64"
        Region.NI -> return "+505"
        Region.NU -> return "+683"
        Region.NO -> return "+47"
        Region.OM -> return "+968"
        Region.PK -> return "+92"
        Region.PW -> return "+680"
        Region.PA -> return "+507"
        Region.PG -> return "+675"
        Region.PY -> return "+595"
        Region.PE -> return "+51"
        Region.PH -> return "+63"
        Region.PL -> return "+48"
        Region.PT -> return "+351"
        Region.PR -> return "+1939"
        Region.QA -> return "+974"
        Region.RO -> return "+40"
        Region.RW -> return "+250"
        Region.WS -> return "+685"
        Region.SM -> return "+378"
        Region.SA -> return "+966"
        Region.SN -> return "+221"
        Region.RS -> return "+381"
        Region.SC -> return "+248"
        Region.SL -> return "+232"
        Region.SG -> return "+65"
        Region.SK -> return "+421"
        Region.SI -> return "+386"
        Region.SO -> return "+252"
        Region.ZA -> return "+27"
        Region.ES -> return "+34"
        Region.LK -> return "+94"
        Region.SD -> return "+249"
        Region.SR -> return "+597"
        Region.SZ -> return "+268"
        Region.SE -> return "+46"
        Region.CH -> return "+41"
        Region.TW -> return "+886"
        Region.TJ -> return "+992"
        Region.TZ -> return "+255"
        Region.TH -> return "+66"
        Region.TL -> return "+670"
        Region.TG -> return "+228"
        Region.TK -> return "+690"
        Region.TO -> return "+676"
        Region.TT -> return "+1868"
        Region.TN -> return "+216"
        Region.TR -> return "+90"
        Region.TM -> return "+993"
        Region.TV -> return "+688"
        Region.UG -> return "+256"
        Region.UA -> return "+380"
        Region.AE -> return "+971"
        Region.GB -> return "+44"
        Region.US -> return "+1"
        Region.UY -> return "+598"
        Region.UZ -> return "+998"
        Region.VU -> return "+678"
        Region.VE -> return "+58"
        Region.VN -> return "+84"
        Region.WF -> return "+681"
        Region.YE -> return "+967"
        Region.ZM -> return "+260"
        Region.ZW -> return "+263"
    }
}

fun Region.phoneNumberDigitsCount(): IntArray {
    when (this) {
        Region.AF -> return intArrayOf(9)
        Region.AL -> return (3..9).toIntArray()
        Region.DZ -> return intArrayOf(8, 9)
        Region.AS -> return intArrayOf(7)
        Region.AD -> return intArrayOf(6, 8, 9)
        Region.AO -> return intArrayOf(9)
        Region.AI -> return intArrayOf(7)
        Region.AG -> return intArrayOf(7)
        Region.AR -> return intArrayOf(10)
        Region.AM -> return intArrayOf(8)
        Region.AW -> return intArrayOf(7)
        Region.AU -> return (5..15).toIntArray()
        Region.AT -> return (4..13).toIntArray()
        Region.AZ -> return intArrayOf(8, 9)
        Region.BS -> return intArrayOf(7)
        Region.BH -> return intArrayOf(8)
        Region.BD -> return (6..10).toIntArray()
        Region.BB -> return intArrayOf(7)
        Region.BY -> return intArrayOf(9)
        Region.BE -> return intArrayOf(8, 9)
        Region.BZ -> return intArrayOf(7)
        Region.BJ -> return intArrayOf(8)
        Region.BM -> return intArrayOf(7)
        Region.BT -> return intArrayOf(7, 8)
        Region.BO -> return intArrayOf(8)
        Region.BA -> return intArrayOf(8)
        Region.BW -> return intArrayOf(7, 8)
        Region.BR -> return intArrayOf(10)
        Region.BN -> return intArrayOf(7)
        Region.BG -> return intArrayOf(7, 8, 9)
        Region.BF -> return intArrayOf(8)
        Region.BI -> return intArrayOf(8)
        Region.KH -> return intArrayOf(8)
        Region.CM -> return intArrayOf(8)
        Region.CA -> return intArrayOf(10)
        Region.CV -> return intArrayOf(7)
        Region.CF -> return intArrayOf(8)
        Region.TD -> return intArrayOf(8)
        Region.CL -> return intArrayOf(8, 9)
        Region.CN -> return (5..12).toIntArray()
        Region.CO -> return intArrayOf(8, 10)
        Region.KM -> return intArrayOf(7)
        Region.CG -> return intArrayOf(9)
        Region.CR -> return intArrayOf(8)
        Region.HR -> return (8..12).toIntArray()
        Region.CU -> return intArrayOf(6, 7, 8)
        Region.CY -> return intArrayOf(8, 11)
        Region.CZ -> return (4..12).toIntArray()
        Region.DK -> return intArrayOf(8)
        Region.EC -> return intArrayOf(8)
        Region.EG -> return intArrayOf(7, 8, 9)
        Region.SV -> return intArrayOf(7, 8, 11)
        Region.GQ -> return intArrayOf(9)
        Region.ER -> return intArrayOf(7)
        Region.EE -> return (7..10).toIntArray()
        Region.ET -> return intArrayOf(9)
        Region.FJ -> return intArrayOf(7)
        Region.FI -> return (5..12).toIntArray()
        Region.FR -> return intArrayOf(9)
        Region.GF -> return intArrayOf(9)
        Region.PF -> return intArrayOf(6)
        Region.GA -> return intArrayOf(6, 7)
        Region.GM -> return intArrayOf(7)
        Region.GE -> return intArrayOf(9)
        Region.DE -> return (6..13).toIntArray()
        Region.GH -> return (5..9).toIntArray()
        Region.GI -> return intArrayOf(8)
        Region.GR -> return intArrayOf(10)
        Region.GL -> return intArrayOf(6)
        Region.GT -> return intArrayOf(8)
        Region.GY -> return intArrayOf(7)
        Region.HT -> return intArrayOf(8)
        Region.VA -> return intArrayOf(8)
        Region.HN -> return intArrayOf(8)
        Region.HK -> return intArrayOf(4, 8, 9)
        Region.HU -> return intArrayOf(8, 9)
        Region.IS -> return intArrayOf(7, 9)
        Region.IN -> return (7..10).toIntArray()
        Region.ID -> return (5..10).toIntArray()
        Region.IR -> return (6..10).toIntArray()
        Region.IQ -> return intArrayOf(8, 9, 10)
        Region.IE -> return (7..11).toIntArray()
        Region.IT -> return intArrayOf(11)
        Region.JM -> return intArrayOf(7)
        Region.JP -> return (5..13).toIntArray()
        Region.JO -> return (5..9).toIntArray()
        Region.KZ -> return intArrayOf(10)
        Region.KE -> return (6..10).toIntArray()
        Region.KR -> return (8..11).toIntArray()
        Region.KW -> return intArrayOf(7, 8)
        Region.KG -> return intArrayOf(9)
        Region.LV -> return intArrayOf(7, 8)
        Region.LB -> return intArrayOf(7, 8)
        Region.LS -> return intArrayOf(8)
        Region.LR -> return intArrayOf(7, 8)
        Region.LI -> return intArrayOf(7, 8, 9)
        Region.LT -> return intArrayOf(8)
        Region.LU -> return (4..11).toIntArray()
        Region.MO -> return intArrayOf(7, 8)
        Region.MY -> return intArrayOf(7, 8, 9)
        Region.ML -> return intArrayOf(8)
        Region.MT -> return intArrayOf(8)
        Region.MU -> return intArrayOf(7)
        Region.MX -> return intArrayOf(10)
        Region.FM -> return intArrayOf(7)
        Region.MD -> return intArrayOf(8)
        Region.MC -> return (5..9).toIntArray()
        Region.MN -> return intArrayOf(7, 8)
        Region.ME -> return (4..12).toIntArray()
        Region.MS -> return intArrayOf(7)
        Region.MA -> return intArrayOf(9)
        Region.MZ -> return intArrayOf(8, 9)
        Region.NP -> return intArrayOf(8, 9)
        Region.NL -> return intArrayOf(9)
        Region.NC -> return intArrayOf(6)
        Region.NZ -> return (3..10).toIntArray()
        Region.NI -> return intArrayOf(8)
        Region.NU -> return intArrayOf(4)
        Region.NO -> return intArrayOf(5, 8)
        Region.OM -> return intArrayOf(7, 8)
        Region.PK -> return (8..11).toIntArray()
        Region.PW -> return intArrayOf(7)
        Region.PA -> return intArrayOf(7, 8)
        Region.PG -> return (4..11).toIntArray()
        Region.PY -> return (5..9).toIntArray()
        Region.PE -> return (8..11).toIntArray()
        Region.PH -> return (8..10).toIntArray()
        Region.PL -> return (6..9).toIntArray()
        Region.PT -> return intArrayOf(9, 11)
        Region.PR -> return intArrayOf(7)
        Region.QA -> return (3..8).toIntArray()
        Region.RO -> return intArrayOf(9)
        Region.RW -> return intArrayOf(9)
        Region.WS -> return (3..7).toIntArray()
        Region.SM -> return (6..10).toIntArray()
        Region.SA -> return intArrayOf(8, 9)
        Region.SN -> return intArrayOf(9)
        Region.RS -> return (4..12).toIntArray()
        Region.SC -> return intArrayOf(7)
        Region.SL -> return intArrayOf(8)
        Region.SG -> return (8..12).toIntArray()
        Region.SK -> return (4..9).toIntArray()
        Region.SI -> return intArrayOf(8)
        Region.SO -> return (5..8).toIntArray()
        Region.ZA -> return intArrayOf(9)
        Region.ES -> return intArrayOf(9)
        Region.LK -> return intArrayOf(9)
        Region.SD -> return intArrayOf(9)
        Region.SR -> return intArrayOf(6, 7)
        Region.SZ -> return intArrayOf(7, 8)
        Region.SE -> return (7..13).toIntArray()
        Region.CH -> return (4..12).toIntArray()
        Region.TW -> return intArrayOf(8, 9)
        Region.TJ -> return intArrayOf(9)
        Region.TZ -> return intArrayOf(9)
        Region.TH -> return intArrayOf(8, 9)
        Region.TL -> return intArrayOf(7)
        Region.TG -> return intArrayOf(8)
        Region.TK -> return intArrayOf(4)
        Region.TO -> return intArrayOf(5, 7)
        Region.TT -> return intArrayOf(7)
        Region.TN -> return intArrayOf(8)
        Region.TR -> return intArrayOf(10)
        Region.TM -> return intArrayOf(8)
        Region.TV -> return intArrayOf(5, 6)
        Region.UG -> return intArrayOf(9)
        Region.UA -> return intArrayOf(9)
        Region.AE -> return intArrayOf(8, 9)
        Region.GB -> return (7..10).toIntArray()
        Region.US -> return intArrayOf(10)
        Region.UY -> return (4..11).toIntArray()
        Region.UZ -> return intArrayOf(9)
        Region.VU -> return intArrayOf(5, 7)
        Region.VE -> return intArrayOf(10)
        Region.VN -> return (7..10).toIntArray()
        Region.WF -> return intArrayOf(6)
        Region.YE -> return (6..9).toIntArray()
        Region.ZM -> return intArrayOf(9)
        Region.ZW -> return (5..10).toIntArray()
    }
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
        val digits = phoneNumberDigitsCount().last()
        (1..digits).joinToString("") { "x" }
    }
}