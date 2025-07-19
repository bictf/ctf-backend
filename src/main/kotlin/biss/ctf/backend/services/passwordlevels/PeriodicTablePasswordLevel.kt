package biss.ctf.backend.services.passwordlevels

class PeriodicTablePasswordLevel : PasswordGameLevel {
    override fun getLevelDescription(): String {
        return "It's chemistry o'clock! Elements in password must add up to $SUM_OF_ATOMIC_ELEMENTS..."
    }

    override fun getLevelHint(): String {
        return "Try looking at a periodic table!"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        var sum = 0
        var passwordToSearch = String(password.toCharArray())
        ELEMENT_MAPPING.toSortedMap(compareBy<String> { -it.length }.thenBy { it }).forEach { (element, atomicNumber) ->
            while (element in passwordToSearch) {
                passwordToSearch = passwordToSearch.replaceFirst(element, "")
                sum += atomicNumber
            }
        }
        return sum == SUM_OF_ATOMIC_ELEMENTS
    }

    companion object {
        const val SUM_OF_ATOMIC_ELEMENTS = 500

        //        THANKS CHATGPT
        val ELEMENT_MAPPING: Map<String, Int> = mapOf(
            "H" to 1,    // Hydrogen
            "He" to 2,   // Helium
            "Li" to 3,   // Lithium
            "Be" to 4,   // Beryllium
            "B" to 5,    // Boron
            "C" to 6,    // Carbon
            "N" to 7,    // Nitrogen
            "O" to 8,    // Oxygen
            "F" to 9,    // Fluorine
            "Ne" to 10,  // Neon
            "Na" to 11,  // Sodium
            "Mg" to 12,  // Magnesium
            "Al" to 13,  // Aluminum
            "Si" to 14,  // Silicon
            "P" to 15,   // Phosphorus
            "S" to 16,   // Sulfur
            "Cl" to 17,  // Chlorine
            "Ar" to 18,  // Argon
            "K" to 19,   // Potassium
            "Ca" to 20,  // Calcium
            "Sc" to 21,  // Scandium
            "Ti" to 22,  // Titanium
            "V" to 23,   // Vanadium
            "Cr" to 24,  // Chromium
            "Mn" to 25,  // Manganese
            "Fe" to 26,  // Iron
            "Co" to 27,  // Cobalt
            "Ni" to 28,  // Nickel
            "Cu" to 29,  // Copper
            "Zn" to 30,  // Zinc
            "Ga" to 31,  // Gallium
            "Ge" to 32,  // Germanium
            "As" to 33,  // Arsenic
            "Se" to 34,  // Selenium
            "Br" to 35,  // Bromine
            "Kr" to 36,  // Krypton
            "Rb" to 37,  // Rubidium
            "Sr" to 38,  // Strontium
            "Y" to 39,   // Yttrium
            "Zr" to 40,  // Zirconium
            "Nb" to 41,  // Niobium
            "Mo" to 42,  // Molybdenum
            "Tc" to 43,  // Technetium
            "Ru" to 44,  // Ruthenium
            "Rh" to 45,  // Rhodium
            "Pd" to 46,  // Palladium
            "Ag" to 47,  // Silver
            "Cd" to 48,  // Cadmium
            "In" to 49,  // Indium
            "Sn" to 50,  // Tin
            "Sb" to 51,  // Antimony
            "Te" to 52,  // Tellurium
            "I" to 53,   // Iodine
            "Xe" to 54,  // Xenon
            "Cs" to 55,  // Cesium
            "Ba" to 56,  // Barium
            "La" to 57,  // Lanthanum
            "Ce" to 58,  // Cerium
            "Pr" to 59,  // Praseodymium
            "Nd" to 60,  // Neodymium
            "Pm" to 61,  // Promethium
            "Sm" to 62,  // Samarium
            "Eu" to 63,  // Europium
            "Gd" to 64,  // Gadolinium
            "Tb" to 65,  // Terbium
            "Dy" to 66,  // Dysprosium
            "Ho" to 67,  // Holmium
            "Er" to 68,  // Erbium
            "Tm" to 69,  // Thulium
            "Yb" to 70,  // Ytterbium
            "Lu" to 71,  // Lutetium
            "Hf" to 72,  // Hafnium
            "Ta" to 73,  // Tantalum
            "W" to 74,   // Tungsten
            "Re" to 75,  // Rhenium
            "Os" to 76,  // Osmium
            "Ir" to 77,  // Iridium
            "Pt" to 78,  // Platinum
            "Au" to 79,  // Gold
            "Hg" to 80,  // Mercury
            "Tl" to 81,  // Thallium
            "Pb" to 82,  // Lead
            "Bi" to 83,  // Bismuth
            "Po" to 84,  // Polonium
            "At" to 85,  // Astatine
            "Rn" to 86,  // Radon
            "Fr" to 87,  // Francium
            "Ra" to 88,  // Radium
            "Ac" to 89,  // Actinium
            "Th" to 90,  // Thorium
            "Pa" to 91,  // Protactinium
            "U" to 92,   // Uranium
            "Np" to 93,  // Neptunium
            "Pu" to 94,  // Plutonium
            "Am" to 95,  // Americium
            "Cm" to 96,  // Curium
            "Bk" to 97,  // Berkelium
            "Cf" to 98,  // Californium
            "Es" to 99,  // Einsteinium
            "Fm" to 100, // Fermium
            "Md" to 101, // Mendelevium
            "No" to 102, // Nobelium
            "Lr" to 103, // Lawrencium
            "Rf" to 104, // Rutherfordium
            "Db" to 105, // Dubnium
            "Sg" to 106, // Seaborgium
            "Bh" to 107, // Bohrium
            "Hs" to 108, // Hassium
            "Mt" to 109, // Meitnerium
            "Ds" to 110, // Darmstadtium
            "Rg" to 111, // Roentgenium
            "Cn" to 112, // Copernicium
            "Nh" to 113, // Nihonium
            "Fl" to 114, // Flerovium
            "Mc" to 115, // Moscovium
            "Lv" to 116, // Livermorium
            "Ts" to 117, // Tennessine
            "Og" to 118  // Oganesson
        )
    }
}