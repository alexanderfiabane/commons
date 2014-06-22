/*
 * Copyright (C) 2013 Marcius da Silva da Fonseca.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA
 */
package br.ojimarcius.commons.util;

/**
 * http://www.ssec.wisc.edu/~tomw/java/unicode.html
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class LatinCharacterUtils {

    @SuppressWarnings("ConstantConditions")
    public static char undecorateLetter(final char c) {
        switch (c) {
            case '\u00C0': // [Latin-1 Supplement] UPPER A GRAVE
            case '\u00C1': // [Latin-1 Supplement] UPPER A ACUTE
            case '\u00C2': // [Latin-1 Supplement] UPPER A CIRCUMFLEX
            case '\u00C3': // [Latin-1 Supplement] UPPER A TILDE
            case '\u00C4': // [Latin-1 Supplement] UPPER A DIAERESIS
            case '\u00C5': // [Latin-1 Supplement] UPPER A RING ABOVE
            case '\u0100': // [Latin-1 Extended-A] UPPER A MACRON
            case '\u0102': // [Latin-1 Extended-A] UPPER A BREVE
            case '\u0104': // [Latin-1 Extended-A] UPPER A OGONEK
            case '\u01CD': // [Latin-1 Extended-B] UPPER A CARON
            case '\u01DE': // [Latin-1 Extended-B] UPPER A DIAERESIS AND MACRON
            case '\u01E0': // [Latin-1 Extended-B] UPPER A DOT ABOVE AND MACRON
            case '\u01FA': // [Latin-1 Extended-B] UPPER A RING ABOVE AND ACUTE
            case '\u0200': // [Latin-1 Extended-B] UPPER A DOUBLE GRAVE
            case '\u0202': // [Latin-1 Extended-B] UPPER A INVERTED BREVE
            case '\u0226': // [Latin-1 Extended-B] UPPER A DOT ABOVE
                return 'A';
            case '\u00E0': // [Latin-1 Supplement] LOWER A WITH GRAVE
            case '\u00E1': // [Latin-1 Supplement] LOWER A WITH ACUTE
            case '\u00E2': // [Latin-1 Supplement] LOWER A WITH CIRCUMFLEX
            case '\u00E3': // [Latin-1 Supplement] LOWER A WITH TILDE
            case '\u00E4': // [Latin-1 Supplement] LOWER A WITH DIAERESIS
            case '\u00E5': // [Latin-1 Supplement] LOWER A WITH RING ABOVE
            case '\u0101': // [Latin-1 Extended-A] LOWER A WITH MACRON
            case '\u0103': // [Latin-1 Extended-A] LOWER A WITH BREVE
            case '\u0105': // [Latin-1 Extended-A] LOWER A WITH OGONEK
            case '\u01CE': // [Latin-1 Extended-B] LOWER A WITH CARON
            case '\u01DF': // [Latin-1 Extended-B] LOWER A WITH DIAERESIS AND MACRON
            case '\u01E1': // [Latin-1 Extended-B] LOWER A WITH DOT ABOVE AND MACRON
            case '\u01FB': // [Latin-1 Extended-B] LOWER A WITH RING ABOVE AND ACUTE
            case '\u0201': // [Latin-1 Extended-B] LOWER A WITH DOUBLE GRAVE
            case '\u0203': // [Latin-1 Extended-B] LOWER A WITH INVERTED BREVE
            case '\u0227': // [Latin-1 Extended-B] LOWER A WITH DOT ABOVE
                return 'a';
            case '\u0181': // [Latin-1 Extended-B] UPPER B WITH HOOK
            case '\u0182': // [Latin-1 Extended-B] UPPER B WITH TOPBAR
                return 'B';
            case '\u0180': // [Latin-1 Extended-B] LOWER B WITH STROKE
            case '\u0183': // [Latin-1 Extended-B] LOWER B WITH TOPBAR
                return 'b';
            case '\u00C7': // [Latin-1 Supplement] UPPER C WITH CEDILLA
            case '\u0106': // [Latin-1 Extended-A] UPPER C WITH ACUTE
            case '\u0108': // [Latin-1 Extended-A] UPPER C WITH CIRCUMFLEX
            case '\u010A': // [Latin-1 Extended-A] UPPER C WITH DOT ABOVE
            case '\u010C': // [Latin-1 Extended-A] UPPER C WITH CARON
            case '\u0187': // [Latin-1 Extended-B] UPPER C WITH HOOK
                return 'C';
            case '\u00E7': // [Latin-1 Supplement] LOWER C WITH CEDILLA
            case '\u0107': // [Latin-1 Extended-A] LOWER C WITH ACUTE
            case '\u0109': // [Latin-1 Extended-A] LOWER C WITH CIRCUMFLEX
            case '\u010B': // [Latin-1 Extended-A] LOWER C WITH DOT ABOVE
            case '\u010D': // [Latin-1 Extended-A] LOWER C WITH CARON
            case '\u0188': // [Latin-1 Extended-B] LOWER C WITH HOOK
                return 'c';
            case '\u010E': // [Latin-1 Extended-A] UPPER D WITH CARON
            case '\u0110': // [Latin-1 Extended-A] UPPER D WITH STROKE
            case '\u01C5': // [Latin-1 Extended-B] UPPER D WITH SMALL LETTER Z WITH CARON
            case '\u01F2': // [Latin-1 Extended-B] UPPER D WITH SMALL LETTER Z
            case '\u0189': // [Latin-1 Extended-B] UPPER AFRICAN D
            case '\u018A': // [Latin-1 Extended-B] UPPER D WITH HOOK
            case '\u018B': // [Latin-1 Extended-B] UPPER D WITH TOPBAR
                return 'D';
            case '\u010F': // [Latin-1 Extended-A] LOWER D WITH CARON
            case '\u0111': // [Latin-1 Extended-A] LOWER D WITH STROKE
            case '\u018C': // [Latin-1 Extended-B] LOWER D WITH TOPBAR
                return 'd';
            case '\u00C8': // [Latin-1 Supplement] UPPER E WITH GRAVE
            case '\u00C9': // [Latin-1 Supplement] UPPER E WITH ACUTE
            case '\u00CA': // [Latin-1 Supplement] UPPER E WITH CIRCUMFLEX
            case '\u00CB': // [Latin-1 Supplement] UPPER E WITH DIAERESIS
            case '\u0112': // [Latin-1 Extended-A] UPPER E WITH MACRON
            case '\u0114': // [Latin-1 Extended-A] UPPER E WITH BREVE
            case '\u0116': // [Latin-1 Extended-A] UPPER E WITH DOT ABOVE
            case '\u0118': // [Latin-1 Extended-A] UPPER E WITH OGONEK
            case '\u011A': // [Latin-1 Extended-A] UPPER E WITH CARON
            case '\u018E': // [Latin-1 Extended-B] UPPER REVERSED E
            case '\u0190': // [Latin-1 Extended-B] UPPER OPEN E
            case '\u0204': // [Latin-1 Extended-B] UPPER E WITH DOUBLE GRAVE
            case '\u0206': // [Latin-1 Extended-B] UPPER E WITH INVERTED BREVE
            case '\u0228': // [Latin-1 Extended-B] UPPER E WITH CEDILLA
                return 'E';
            case '\u00E8': // [Latin-1 Supplement] LOWER E WITH GRAVE
            case '\u00E9': // [Latin-1 Supplement] LOWER E WITH ACUTE
            case '\u00EA': // [Latin-1 Supplement] LOWER E WITH CIRCUMFLEX
            case '\u00EB': // [Latin-1 Supplement] LOWER E WITH DIAERESIS
            case '\u0113': // [Latin-1 Extended-A] LOWER E WITH MACRON
            case '\u0115': // [Latin-1 Extended-A] LOWER E WITH BREVE
            case '\u0117': // [Latin-1 Extended-A] LOWER E WITH DOT ABOVE
            case '\u0119': // [Latin-1 Extended-A] LOWER E WITH OGONEK
            case '\u011B': // [Latin-1 Extended-A] LOWER E WITH CARON
            case '\u01DD': // [Latin-1 Extended-B] LOWER TURNED E
            case '\u0205': // [Latin-1 Extended-B] LOWER E WITH DOUBLE GRAVE
            case '\u0207': // [Latin-1 Extended-B] LOWER E WITH INVERTED BREVE
            case '\u0229': // [Latin-1 Extended-B] LOWER E WITH CEDILLA
                return 'e';
            case '\u0191': // [Latin-1 Extended-B] UPPER F WITH HOOK
                return 'F';
            case '\u0192': // [Latin-1 Extended-B] LOWER F WITH HOOK
                return 'f';
            case '\u011C': // [Latin-1 Extended-A] UPPER G WITH CIRCUMFLEX
            case '\u011E': // [Latin-1 Extended-A] UPPER G WITH BREVE
            case '\u0120': // [Latin-1 Extended-A] UPPER G WITH DOT ABOVE
            case '\u0122': // [Latin-1 Extended-A] UPPER G WITH CEDILLA
            case '\u0193': // [Latin-1 Extended-B] UPPER G WITH HOOK
            case '\u01E4': // [Latin-1 Extended-B] UPPER G WITH STROKE
            case '\u01E6': // [Latin-1 Extended-B] UPPER G WITH CARON
            case '\u01F4': // [Latin-1 Extended-B] UPPER G WITH ACUTE
                return 'G';
            case '\u011D': // [Latin-1 Extended-A] LOWER G WITH CIRCUMFLEX
            case '\u011F': // [Latin-1 Extended-A] LOWER G WITH BREVE
            case '\u0121': // [Latin-1 Extended-A] LOWER G WITH DOT ABOVE
            case '\u0123': // [Latin-1 Extended-A] LOWER G WITH CEDILLA
            case '\u01E5': // [Latin-1 Extended-B] LOWER G WITH STROKE
            case '\u01E7': // [Latin-1 Extended-B] LOWER G WITH CARON
            case '\u01F5': // [Latin-1 Extended-B] LOWER G WITH ACUTE
                return 'g';
            case '\u0124': // [Latin-1 Extended-A] UPPER H WITH CIRCUMFLEX
            case '\u0126': // [Latin-1 Extended-A] UPPER H WITH STROKE
            case '\u021E': // [Latin-1 Extended-B] UPPER H WITH CARON
                return 'H';
            case '\u0125': // [Latin-1 Extended-A] LOWER H WITH CIRCUMFLEX
            case '\u0127': // [Latin-1 Extended-A] LOWER H WITH STROKE
            case '\u021F': // [Latin-1 Extended-B] LOWER H WITH CARON
                return 'h';
            case '\u00CC': // [Latin-1 Supplement] UPPER I WITH GRAVE
            case '\u00CD': // [Latin-1 Supplement] UPPER I WITH ACUTE
            case '\u00CE': // [Latin-1 Supplement] UPPER I WITH CIRCUMFLEX
            case '\u00CF': // [Latin-1 Supplement] UPPER I WITH DIAERESIS
            case '\u0128': // [Latin-1 Extended-A] UPPER I WITH TILDE
            case '\u012A': // [Latin-1 Extended-A] UPPER I WITH MACRON
            case '\u012C': // [Latin-1 Extended-A] UPPER I WITH BREVE
            case '\u012E': // [Latin-1 Extended-A] UPPER I WITH OGONEK
            case '\u0130': // [Latin-1 Extended-A] UPPER I WITH DOT ABOVE
            case '\u0197': // [Latin-1 Extended-B] UPPER I WITH STROKE
            case '\u01CF': // [Latin-1 Extended-B] UPPER I WITH CARON
            case '\u0208': // [Latin-1 Extended-B] UPPER I WITH DOUBLE GRAVE
            case '\u020A': // [Latin-1 Extended-B] UPPER I WITH INVERTED BREVE
                return 'I';
            case '\u00EC': // [Latin-1 Supplement] LOWER I WITH GRAVE
            case '\u00ED': // [Latin-1 Supplement] LOWER I WITH ACUTE
            case '\u00EE': // [Latin-1 Supplement] LOWER I WITH CIRCUMFLEX
            case '\u00EF': // [Latin-1 Supplement] LOWER I WITH DIAERESIS
            case '\u0129': // [Latin-1 Extended-A] LOWER I WITH TILDE
            case '\u012B': // [Latin-1 Extended-A] LOWER I WITH MACRON
            case '\u012D': // [Latin-1 Extended-A] LOWER I WITH BREVE
            case '\u012F': // [Latin-1 Extended-A] LOWER I WITH OGONEK
            case '\u0131': // [Latin-1 Extended-A] LOWER DOTLESS I
            case '\u01D0': // [Latin-1 Extended-B] LOWER I WITH CARON
            case '\u0209': // [Latin-1 Extended-B] LOWER I WITH DOUBLE GRAVE
            case '\u020B': // [Latin-1 Extended-B] LOWER I WITH INVERTED BREVE
                return 'i';
            case '\u0134': // [Latin-1 Extended-A] UPPER J WITH CIRCUMFLEX
                return 'J';
            case '\u0135': // [Latin-1 Extended-A] LOWER J WITH CIRCUMFLEX
            case '\u01F0': // [Latin-1 Extended-B] LOWER J WITH CARON
                return 'j';
            case '\u0136': // [Latin-1 Extended-A] UPPER K WITH CEDILLA
            case '\u0198': // [Latin-1 Extended-B] UPPER K WITH HOOK
            case '\u01E8': // [Latin-1 Extended-B] UPPER K WITH CARON
                return 'K';
            case '\u0137': // [Latin-1 Extended-A] LOWER K WITH CEDILLA
            case '\u0138': // [Latin-1 Extended-A] LOWER KRA
            case '\u0199': // [Latin-1 Extended-B] LOWER K WITH HOOK
            case '\u01E9': // [Latin-1 Extended-B] LOWER K WITH CARON
                return 'k';
            case '\u0139': // [Latin-1 Extended-A] UPPER L WITH ACUTE
            case '\u013B': // [Latin-1 Extended-A] UPPER L WITH CEDILLA
            case '\u013D': // [Latin-1 Extended-A] UPPER L WITH CARON
            case '\u013F': // [Latin-1 Extended-A] UPPER L WITH MIDDLE DOT
            case '\u0141': // [Latin-1 Extended-A] UPPER L WITH STROKE
                return 'L';
            case '\u013A': // [Latin-1 Extended-A] LOWER L WITH ACUTE
            case '\u013C': // [Latin-1 Extended-A] LOWER L WITH CEDILLA
            case '\u013E': // [Latin-1 Extended-A] LOWER L WITH CARON
            case '\u0140': // [Latin-1 Extended-A] LOWER L WITH MIDDLE DOT
            case '\u0142': // [Latin-1 Extended-A] LOWER L WITH STROKE
            case '\u019A': // [Latin-1 Extended-B] LOWER L WITH BAR
                return 'l';
            case '\u019C': // [Latin-1 Extended-B] UPPER TURNED M
                return 'M';
            case '\u00D1': // [Latin-1 Supplement] UPPER N WITH TILDE
            case '\u0143': // [Latin-1 Extended-A] UPPER N WITH ACUTE
            case '\u0145': // [Latin-1 Extended-A] UPPER N WITH CEDILLA
            case '\u0147': // [Latin-1 Extended-A] UPPER N WITH CARON
            case '\u019D': // [Latin-1 Extended-B] UPPER N WITH LEFT HOOK
            case '\u01F8': // [Latin-1 Extended-B] UPPER N WITH GRAVE
                return 'N';
            case '\u00F1': // [Latin-1 Supplement] LOWER N WITH TILDE
            case '\u0144': // [Latin-1 Extended-A] LOWER N WITH ACUTE
            case '\u0146': // [Latin-1 Extended-A] LOWER N WITH CEDILLA
            case '\u0148': // [Latin-1 Extended-A] LOWER N WITH CARON
            case '\u0149': // [Latin-1 Extended-A] LOWER N PRECEDED BY APOSTROPHE
            case '\u019E': // [Latin-1 Extended-B] LOWER N WITH LONG RIGHT LEG
            case '\u01F9': // [Latin-1 Extended-B] LOWER N WITH GRAVE
                return 'n';
            case '\u00D2': // [Latin-1 Supplement] UPPER O WITH GRAVE
            case '\u00D3': // [Latin-1 Supplement] UPPER O WITH ACUTE
            case '\u00D4': // [Latin-1 Supplement] UPPER O WITH CIRCUMFLEX
            case '\u00D5': // [Latin-1 Supplement] UPPER O WITH TILDE
            case '\u00D6': // [Latin-1 Supplement] UPPER O WITH DIAERESIS
            case '\u00D8': // [Latin-1 Supplement] UPPER O WITH STROKE
            case '\u014C': // [Latin-1 Extended-A] UPPER O WITH MACRON
            case '\u014E': // [Latin-1 Extended-A] UPPER O WITH BREVE
            case '\u0150': // [Latin-1 Extended-A] UPPER O WITH DOUBLE ACUTE
            case '\u0186': // [Latin-1 Extended-B] UPPER OPEN O
            case '\u019F': // [Latin-1 Extended-B] UPPER O WITH MIDDLE TILDE
            case '\u01A0': // [Latin-1 Extended-B] UPPER O WITH HORN
            case '\u01D1': // [Latin-1 Extended-B] UPPER O WITH CARON
            case '\u01EA': // [Latin-1 Extended-B] UPPER O WITH OGONEK
            case '\u01EC': // [Latin-1 Extended-B] UPPER O WITH OGONEK AND MACRON
            case '\u01FE': // [Latin-1 Extended-B] UPPER O WITH STROKE AND ACUTE
            case '\u020C': // [Latin-1 Extended-B] UPPER O WITH DOUBLE GRAVE
            case '\u020E': // [Latin-1 Extended-B] UPPER O WITH INVERTED BREVE
            case '\u022A': // [Latin-1 Extended-B] UPPER O WITH DIAERESIS AND MACRON
            case '\u022C': // [Latin-1 Extended-B] UPPER O WITH TILDE AND MACRON
            case '\u022E': // [Latin-1 Extended-B] UPPER O WITH DOT ABOVE
            case '\u0230': // [Latin-1 Extended-B] UPPER O WITH DOT ABOVE AND MACRON
                return 'O';
            case '\u00F2': // [Latin-1 Supplement] LOWER O WITH GRAVE
            case '\u00F3': // [Latin-1 Supplement] LOWER O WITH ACUTE
            case '\u00F4': // [Latin-1 Supplement] LOWER O WITH CIRCUMFLEX
            case '\u00F5': // [Latin-1 Supplement] LOWER O WITH TILDE
            case '\u00F6': // [Latin-1 Supplement] LOWER O WITH DIAERESIS
            case '\u00F8': // [Latin-1 Supplement] LOWER O WITH STROKE
            case '\u014D': // [Latin-1 Extended-A] LOWER O WITH MACRON
            case '\u014F': // [Latin-1 Extended-A] LOWER O WITH BREVE
            case '\u0151': // [Latin-1 Extended-A] LOWER O WITH DOUBLE ACUTE
            case '\u01A1': // [Latin-1 Extended-B] LOWER O WITH HORN
            case '\u01D2': // [Latin-1 Extended-B] LOWER O WITH CARON
            case '\u01EB': // [Latin-1 Extended-B] LOWER O WITH OGONEK
            case '\u01ED': // [Latin-1 Extended-B] LOWER O WITH OGONEK AND MACRON
            case '\u01FF': // [Latin-1 Extended-B] LOWER O WITH STROKE AND ACUTE
            case '\u020D': // [Latin-1 Extended-B] LOWER O WITH DOUBLE GRAVE
            case '\u020F': // [Latin-1 Extended-B] LOWER O WITH INVERTED BREVE
            case '\u022B': // [Latin-1 Extended-B] LOWER O WITH DIAERESIS AND MACRON
            case '\u022D': // [Latin-1 Extended-B] LOWER O WITH TILDE AND MACRON
            case '\u022F': // [Latin-1 Extended-B] LOWER O WITH DOT ABOVE
            case '\u0231': // [Latin-1 Extended-B] LOWER O WITH DOT ABOVE AND MACRON
                return 'o';
            case '\u01A4': // [Latin-1 Extended-B] UPPER P WITH HOOK
                return 'P';
            case '\u01A5': // [Latin-1 Extended-B] LOWER P WITH HOOK
                return 'p';
            case '\u0154': // [Latin-1 Extended-A] UPPER R WITH ACUTE
            case '\u0156': // [Latin-1 Extended-A] UPPER R WITH CEDILLA
            case '\u0158': // [Latin-1 Extended-A] UPPER R WITH CARON
            case '\u0210': // [Latin-1 Extended-B] UPPER R WITH DOUBLE GRAVE
            case '\u0212': // [Latin-1 Extended-B] UPPER R WITH INVERTED BREVE
                return 'R';
            case '\u0155': // [Latin-1 Extended-A] LOWER R WITH ACUTE
            case '\u0157': // [Latin-1 Extended-A] LOWER R WITH CEDILLA
            case '\u0159': // [Latin-1 Extended-A] LOWER R WITH CARON
            case '\u0211': // [Latin-1 Extended-B] LOWER R WITH DOUBLE GRAVE
            case '\u0213': // [Latin-1 Extended-B] LOWER R WITH INVERTED BREVE
                return 'r';
            case '\u015A': // [Latin-1 Extended-A] UPPER S WITH ACUTE
            case '\u015C': // [Latin-1 Extended-A] UPPER S WITH CIRCUMFLEX
            case '\u015E': // [Latin-1 Extended-A] UPPER S WITH CEDILLA
            case '\u0160': // [Latin-1 Extended-A] UPPER S WITH CARON
            case '\u0218': // [Latin-1 Extended-B] UPPER S WITH COMMA BELOW
                return 'S';
            case '\u015B': // [Latin-1 Extended-A] LOWER S WITH ACUTE
            case '\u015D': // [Latin-1 Extended-A] LOWER S WITH CIRCUMFLEX
            case '\u015F': // [Latin-1 Extended-A] LOWER S WITH CEDILLA
            case '\u0161': // [Latin-1 Extended-A] LOWER S WITH CARON
            case '\u017F': // [Latin-1 Extended-A] LOWER LONG S
            case '\u0219': // [Latin-1 Extended-B] LOWER S WITH COMMA BELOW
                return 's';
            case '\u0162': // [Latin-1 Extended-A] UPPER T WITH CEDILLA
            case '\u0164': // [Latin-1 Extended-A] UPPER T WITH CARON
            case '\u0166': // [Latin-1 Extended-A] UPPER T WITH STROKE
            case '\u01AC': // [Latin-1 Extended-B] UPPER T WITH HOOK
            case '\u01AE': // [Latin-1 Extended-B] UPPER T WITH RETROFLEX HOOK
            case '\u021A': // [Latin-1 Extended-B] UPPER T WITH COMMA BELOW
                return 'T';
            case '\u0163': // [Latin-1 Extended-A] LOWER T WITH CEDILLA
            case '\u0165': // [Latin-1 Extended-A] LOWER T WITH CARON
            case '\u0167': // [Latin-1 Extended-A] LOWER T WITH STROKE
            case '\u01AB': // [Latin-1 Extended-B] LOWER T WITH PALATAL HOOK
            case '\u01AD': // [Latin-1 Extended-B] LOWER T WITH HOOK
            case '\u021B': // [Latin-1 Extended-B] LOWER T WITH COMMA BELOW
                return 't';
            case '\u00D9': // [Latin-1 Supplement] UPPER U WITH GRAVE
            case '\u00DA': // [Latin-1 Supplement] UPPER U WITH ACUTE
            case '\u00DB': // [Latin-1 Supplement] UPPER U WITH CIRCUMFLEX
            case '\u00DC': // [Latin-1 Supplement] UPPER U WITH DIAERESIS
            case '\u0168': // [Latin-1 Extended-A] UPPER U WITH TILDE
            case '\u016A': // [Latin-1 Extended-A] UPPER U WITH MACRON
            case '\u016C': // [Latin-1 Extended-A] UPPER U WITH BREVE
            case '\u016E': // [Latin-1 Extended-A] UPPER U WITH RING ABOVE
            case '\u0170': // [Latin-1 Extended-A] UPPER U WITH DOUBLE ACUTE
            case '\u0172': // [Latin-1 Extended-A] UPPER U WITH OGONEK
            case '\u01AF': // [Latin-1 Extended-B] UPPER U WITH HORN
            case '\u01D3': // [Latin-1 Extended-B] UPPER U WITH CARON
            case '\u01D5': // [Latin-1 Extended-B] UPPER U WITH DIAERESIS AND MACRON
            case '\u01D7': // [Latin-1 Extended-B] UPPER U WITH DIAERESIS AND ACUTE
            case '\u01D9': // [Latin-1 Extended-B] UPPER U WITH DIAERESIS AND CARON
            case '\u01DB': // [Latin-1 Extended-B] UPPER U WITH DIAERESIS AND GRAVE
            case '\u0214': // [Latin-1 Extended-B] UPPER U WITH DOUBLE GRAVE
            case '\u0216': // [Latin-1 Extended-B] UPPER U WITH INVERTED BREVE
                return 'U';
            case '\u00F9': // [Latin-1 Supplement] LOWER U WITH GRAVE
            case '\u00FA': // [Latin-1 Supplement] LOWER U WITH ACUTE
            case '\u00FB': // [Latin-1 Supplement] LOWER U WITH CIRCUMFLEX
            case '\u00FC': // [Latin-1 Supplement] LOWER U WITH DIAERESIS
            case '\u0169': // [Latin-1 Extended-A] LOWER U WITH TILDE
            case '\u016B': // [Latin-1 Extended-A] LOWER U WITH MACRON
            case '\u016D': // [Latin-1 Extended-A] LOWER U WITH BREVE
            case '\u016F': // [Latin-1 Extended-A] LOWER U WITH RING ABOVE
            case '\u0171': // [Latin-1 Extended-A] LOWER U WITH DOUBLE ACUTE
            case '\u0173': // [Latin-1 Extended-A] LOWER U WITH OGONEK
            case '\u01B0': // [Latin-1 Extended-B] LOWER U WITH HORN
            case '\u01D4': // [Latin-1 Extended-B] LOWER U WITH CARON
            case '\u01D6': // [Latin-1 Extended-B] LOWER U WITH DIAERESIS AND MACRON
            case '\u01D8': // [Latin-1 Extended-B] LOWER U WITH DIAERESIS AND ACUTE
            case '\u01DA': // [Latin-1 Extended-B] LOWER U WITH DIAERESIS AND CARON
            case '\u01DC': // [Latin-1 Extended-B] LOWER U WITH DIAERESIS AND GRAVE
            case '\u0215': // [Latin-1 Extended-B] LOWER U WITH DOUBLE GRAVE
            case '\u0217': // [Latin-1 Extended-B] LOWER U WITH INVERTED BREVE
                return 'u';
            case '\u0174': // [Latin-1 Extended-A] UPPER W WITH CIRCUMFLEX
                return 'W';
            case '\u0175': // [Latin-1 Extended-A] LOWER W WITH CIRCUMFLEX
                return 'w';
            case '\u00DD': // [Latin-1 Supplement] UPPER Y WITH ACUTE
            case '\u0176': // [Latin-1 Extended-A] UPPER Y WITH CIRCUMFLEX
            case '\u0178': // [Latin-1 Extended-A] UPPER Y WITH DIAERESIS
            case '\u01B3': // [Latin-1 Extended-B] UPPER Y WITH HOOK
            case '\u0232': // [Latin-1 Extended-B] UPPER Y WITH MACRON
                return 'Y';
            case '\u00FD': // [Latin-1 Supplement] LOWER Y WITH ACUTE
            case '\u00FF': // [Latin-1 Supplement] LOWER Y WITH DIAERESIS
            case '\u0177': // [Latin-1 Extended-A] LOWER Y WITH CIRCUMFLEX
            case '\u01B4': // [Latin-1 Extended-B] LOWER Y WITH HOOK
            case '\u0233': // [Latin-1 Extended-B] LOWER Y WITH MACRON
                return 'y';
            case '\u0179': // [Latin-1 Extended-A] UPPER Z WITH ACUTE
            case '\u017B': // [Latin-1 Extended-A] UPPER Z WITH DOT ABOVE
            case '\u017D': // [Latin-1 Extended-A] UPPER Z WITH CARON
                return 'Z';
            case '\u017A': // [Latin-1 Extended-A] LOWER Z WITH ACUTE
            case '\u017C': // [Latin-1 Extended-A] LOWER Z WITH DOT ABOVE
            case '\u017E': // [Latin-1 Extended-A] LOWER Z WITH CARON
                return 'z';
            case '\u01B2': // [Latin-1 Extended-B] UPPER V WITH HOOK
                return 'V';
            case '\u01B5': // [Latin-1 Extended-B] UPPER Z WITH STROKE
            case '\u0224': // [Latin-1 Extended-B] UPPER Z WITH HOOK
                return 'Z';
            case '\u01B6': // [Latin-1 Extended-B] LOWER Z WITH STROKE
            case '\u0225': // [Latin-1 Extended-B] LOWER Z WITH HOOK
                return 'z';
            default:
                return c;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean isDecoratedLetter(final char c) {
        switch (c) {
            case '\u00C0': // [Latin-1 Supplement] UPPER A GRAVE
            case '\u00C1': // [Latin-1 Supplement] UPPER A ACUTE
            case '\u00C2': // [Latin-1 Supplement] UPPER A CIRCUMFLEX
            case '\u00C3': // [Latin-1 Supplement] UPPER A TILDE
            case '\u00C4': // [Latin-1 Supplement] UPPER A DIAERESIS
            case '\u00C5': // [Latin-1 Supplement] UPPER A RING ABOVE
            case '\u0100': // [Latin-1 Extended-A] UPPER A MACRON
            case '\u0102': // [Latin-1 Extended-A] UPPER A BREVE
            case '\u0104': // [Latin-1 Extended-A] UPPER A OGONEK
            case '\u01CD': // [Latin-1 Extended-B] UPPER A CARON
            case '\u01DE': // [Latin-1 Extended-B] UPPER A DIAERESIS AND MACRON
            case '\u01E0': // [Latin-1 Extended-B] UPPER A DOT ABOVE AND MACRON
            case '\u01FA': // [Latin-1 Extended-B] UPPER A RING ABOVE AND ACUTE
            case '\u0200': // [Latin-1 Extended-B] UPPER A DOUBLE GRAVE
            case '\u0202': // [Latin-1 Extended-B] UPPER A INVERTED BREVE
            case '\u0226': // [Latin-1 Extended-B] UPPER A DOT ABOVE
            case '\u00E0': // [Latin-1 Supplement] LOWER A WITH GRAVE
            case '\u00E1': // [Latin-1 Supplement] LOWER A WITH ACUTE
            case '\u00E2': // [Latin-1 Supplement] LOWER A WITH CIRCUMFLEX
            case '\u00E3': // [Latin-1 Supplement] LOWER A WITH TILDE
            case '\u00E4': // [Latin-1 Supplement] LOWER A WITH DIAERESIS
            case '\u00E5': // [Latin-1 Supplement] LOWER A WITH RING ABOVE
            case '\u0101': // [Latin-1 Extended-A] LOWER A WITH MACRON
            case '\u0103': // [Latin-1 Extended-A] LOWER A WITH BREVE
            case '\u0105': // [Latin-1 Extended-A] LOWER A WITH OGONEK
            case '\u01CE': // [Latin-1 Extended-B] LOWER A WITH CARON
            case '\u01DF': // [Latin-1 Extended-B] LOWER A WITH DIAERESIS AND MACRON
            case '\u01E1': // [Latin-1 Extended-B] LOWER A WITH DOT ABOVE AND MACRON
            case '\u01FB': // [Latin-1 Extended-B] LOWER A WITH RING ABOVE AND ACUTE
            case '\u0201': // [Latin-1 Extended-B] LOWER A WITH DOUBLE GRAVE
            case '\u0203': // [Latin-1 Extended-B] LOWER A WITH INVERTED BREVE
            case '\u0227': // [Latin-1 Extended-B] LOWER A WITH DOT ABOVE
            case '\u0181': // [Latin-1 Extended-B] UPPER B WITH HOOK
            case '\u0182': // [Latin-1 Extended-B] UPPER B WITH TOPBAR
            case '\u0180': // [Latin-1 Extended-B] LOWER B WITH STROKE
            case '\u0183': // [Latin-1 Extended-B] LOWER B WITH TOPBAR
            case '\u00C7': // [Latin-1 Supplement] UPPER C WITH CEDILLA
            case '\u0106': // [Latin-1 Extended-A] UPPER C WITH ACUTE
            case '\u0108': // [Latin-1 Extended-A] UPPER C WITH CIRCUMFLEX
            case '\u010A': // [Latin-1 Extended-A] UPPER C WITH DOT ABOVE
            case '\u010C': // [Latin-1 Extended-A] UPPER C WITH CARON
            case '\u0187': // [Latin-1 Extended-B] UPPER C WITH HOOK
            case '\u00E7': // [Latin-1 Supplement] LOWER C WITH CEDILLA
            case '\u0107': // [Latin-1 Extended-A] LOWER C WITH ACUTE
            case '\u0109': // [Latin-1 Extended-A] LOWER C WITH CIRCUMFLEX
            case '\u010B': // [Latin-1 Extended-A] LOWER C WITH DOT ABOVE
            case '\u010D': // [Latin-1 Extended-A] LOWER C WITH CARON
            case '\u0188': // [Latin-1 Extended-B] LOWER C WITH HOOK
            case '\u010E': // [Latin-1 Extended-A] UPPER D WITH CARON
            case '\u0110': // [Latin-1 Extended-A] UPPER D WITH STROKE
            case '\u01C5': // [Latin-1 Extended-B] UPPER D WITH SMALL LETTER Z WITH CARON
            case '\u01F2': // [Latin-1 Extended-B] UPPER D WITH SMALL LETTER Z
            case '\u0189': // [Latin-1 Extended-B] UPPER AFRICAN D
            case '\u018A': // [Latin-1 Extended-B] UPPER D WITH HOOK
            case '\u018B': // [Latin-1 Extended-B] UPPER D WITH TOPBAR
            case '\u010F': // [Latin-1 Extended-A] LOWER D WITH CARON
            case '\u0111': // [Latin-1 Extended-A] LOWER D WITH STROKE
            case '\u018C': // [Latin-1 Extended-B] LOWER D WITH TOPBAR
            case '\u00C8': // [Latin-1 Supplement] UPPER E WITH GRAVE
            case '\u00C9': // [Latin-1 Supplement] UPPER E WITH ACUTE
            case '\u00CA': // [Latin-1 Supplement] UPPER E WITH CIRCUMFLEX
            case '\u00CB': // [Latin-1 Supplement] UPPER E WITH DIAERESIS
            case '\u0112': // [Latin-1 Extended-A] UPPER E WITH MACRON
            case '\u0114': // [Latin-1 Extended-A] UPPER E WITH BREVE
            case '\u0116': // [Latin-1 Extended-A] UPPER E WITH DOT ABOVE
            case '\u0118': // [Latin-1 Extended-A] UPPER E WITH OGONEK
            case '\u011A': // [Latin-1 Extended-A] UPPER E WITH CARON
            case '\u018E': // [Latin-1 Extended-B] UPPER REVERSED E
            case '\u0190': // [Latin-1 Extended-B] UPPER OPEN E
            case '\u0204': // [Latin-1 Extended-B] UPPER E WITH DOUBLE GRAVE
            case '\u0206': // [Latin-1 Extended-B] UPPER E WITH INVERTED BREVE
            case '\u0228': // [Latin-1 Extended-B] UPPER E WITH CEDILLA
            case '\u00E8': // [Latin-1 Supplement] LOWER E WITH GRAVE
            case '\u00E9': // [Latin-1 Supplement] LOWER E WITH ACUTE
            case '\u00EA': // [Latin-1 Supplement] LOWER E WITH CIRCUMFLEX
            case '\u00EB': // [Latin-1 Supplement] LOWER E WITH DIAERESIS
            case '\u0113': // [Latin-1 Extended-A] LOWER E WITH MACRON
            case '\u0115': // [Latin-1 Extended-A] LOWER E WITH BREVE
            case '\u0117': // [Latin-1 Extended-A] LOWER E WITH DOT ABOVE
            case '\u0119': // [Latin-1 Extended-A] LOWER E WITH OGONEK
            case '\u011B': // [Latin-1 Extended-A] LOWER E WITH CARON
            case '\u01DD': // [Latin-1 Extended-B] LOWER TURNED E
            case '\u0205': // [Latin-1 Extended-B] LOWER E WITH DOUBLE GRAVE
            case '\u0207': // [Latin-1 Extended-B] LOWER E WITH INVERTED BREVE
            case '\u0229': // [Latin-1 Extended-B] LOWER E WITH CEDILLA
            case '\u0191': // [Latin-1 Extended-B] UPPER F WITH HOOK
            case '\u0192': // [Latin-1 Extended-B] LOWER F WITH HOOK
            case '\u011C': // [Latin-1 Extended-A] UPPER G WITH CIRCUMFLEX
            case '\u011E': // [Latin-1 Extended-A] UPPER G WITH BREVE
            case '\u0120': // [Latin-1 Extended-A] UPPER G WITH DOT ABOVE
            case '\u0122': // [Latin-1 Extended-A] UPPER G WITH CEDILLA
            case '\u0193': // [Latin-1 Extended-B] UPPER G WITH HOOK
            case '\u01E4': // [Latin-1 Extended-B] UPPER G WITH STROKE
            case '\u01E6': // [Latin-1 Extended-B] UPPER G WITH CARON
            case '\u01F4': // [Latin-1 Extended-B] UPPER G WITH ACUTE
            case '\u011D': // [Latin-1 Extended-A] LOWER G WITH CIRCUMFLEX
            case '\u011F': // [Latin-1 Extended-A] LOWER G WITH BREVE
            case '\u0121': // [Latin-1 Extended-A] LOWER G WITH DOT ABOVE
            case '\u0123': // [Latin-1 Extended-A] LOWER G WITH CEDILLA
            case '\u01E5': // [Latin-1 Extended-B] LOWER G WITH STROKE
            case '\u01E7': // [Latin-1 Extended-B] LOWER G WITH CARON
            case '\u01F5': // [Latin-1 Extended-B] LOWER G WITH ACUTE
            case '\u0124': // [Latin-1 Extended-A] UPPER H WITH CIRCUMFLEX
            case '\u0126': // [Latin-1 Extended-A] UPPER H WITH STROKE
            case '\u021E': // [Latin-1 Extended-B] UPPER H WITH CARON
            case '\u0125': // [Latin-1 Extended-A] LOWER H WITH CIRCUMFLEX
            case '\u0127': // [Latin-1 Extended-A] LOWER H WITH STROKE
            case '\u021F': // [Latin-1 Extended-B] LOWER H WITH CARON
            case '\u00CC': // [Latin-1 Supplement] UPPER I WITH GRAVE
            case '\u00CD': // [Latin-1 Supplement] UPPER I WITH ACUTE
            case '\u00CE': // [Latin-1 Supplement] UPPER I WITH CIRCUMFLEX
            case '\u00CF': // [Latin-1 Supplement] UPPER I WITH DIAERESIS
            case '\u0128': // [Latin-1 Extended-A] UPPER I WITH TILDE
            case '\u012A': // [Latin-1 Extended-A] UPPER I WITH MACRON
            case '\u012C': // [Latin-1 Extended-A] UPPER I WITH BREVE
            case '\u012E': // [Latin-1 Extended-A] UPPER I WITH OGONEK
            case '\u0130': // [Latin-1 Extended-A] UPPER I WITH DOT ABOVE
            case '\u0197': // [Latin-1 Extended-B] UPPER I WITH STROKE
            case '\u01CF': // [Latin-1 Extended-B] UPPER I WITH CARON
            case '\u0208': // [Latin-1 Extended-B] UPPER I WITH DOUBLE GRAVE
            case '\u020A': // [Latin-1 Extended-B] UPPER I WITH INVERTED BREVE
            case '\u00EC': // [Latin-1 Supplement] LOWER I WITH GRAVE
            case '\u00ED': // [Latin-1 Supplement] LOWER I WITH ACUTE
            case '\u00EE': // [Latin-1 Supplement] LOWER I WITH CIRCUMFLEX
            case '\u00EF': // [Latin-1 Supplement] LOWER I WITH DIAERESIS
            case '\u0129': // [Latin-1 Extended-A] LOWER I WITH TILDE
            case '\u012B': // [Latin-1 Extended-A] LOWER I WITH MACRON
            case '\u012D': // [Latin-1 Extended-A] LOWER I WITH BREVE
            case '\u012F': // [Latin-1 Extended-A] LOWER I WITH OGONEK
            case '\u0131': // [Latin-1 Extended-A] LOWER DOTLESS I
            case '\u01D0': // [Latin-1 Extended-B] LOWER I WITH CARON
            case '\u0209': // [Latin-1 Extended-B] LOWER I WITH DOUBLE GRAVE
            case '\u020B': // [Latin-1 Extended-B] LOWER I WITH INVERTED BREVE
            case '\u0134': // [Latin-1 Extended-A] UPPER J WITH CIRCUMFLEX
            case '\u0135': // [Latin-1 Extended-A] LOWER J WITH CIRCUMFLEX
            case '\u01F0': // [Latin-1 Extended-B] LOWER J WITH CARON
            case '\u0136': // [Latin-1 Extended-A] UPPER K WITH CEDILLA
            case '\u0198': // [Latin-1 Extended-B] UPPER K WITH HOOK
            case '\u01E8': // [Latin-1 Extended-B] UPPER K WITH CARON
            case '\u0137': // [Latin-1 Extended-A] LOWER K WITH CEDILLA
            case '\u0138': // [Latin-1 Extended-A] LOWER KRA
            case '\u0199': // [Latin-1 Extended-B] LOWER K WITH HOOK
            case '\u01E9': // [Latin-1 Extended-B] LOWER K WITH CARON
            case '\u0139': // [Latin-1 Extended-A] UPPER L WITH ACUTE
            case '\u013B': // [Latin-1 Extended-A] UPPER L WITH CEDILLA
            case '\u013D': // [Latin-1 Extended-A] UPPER L WITH CARON
            case '\u013F': // [Latin-1 Extended-A] UPPER L WITH MIDDLE DOT
            case '\u0141': // [Latin-1 Extended-A] UPPER L WITH STROKE
            case '\u013A': // [Latin-1 Extended-A] LOWER L WITH ACUTE
            case '\u013C': // [Latin-1 Extended-A] LOWER L WITH CEDILLA
            case '\u013E': // [Latin-1 Extended-A] LOWER L WITH CARON
            case '\u0140': // [Latin-1 Extended-A] LOWER L WITH MIDDLE DOT
            case '\u0142': // [Latin-1 Extended-A] LOWER L WITH STROKE
            case '\u019A': // [Latin-1 Extended-B] LOWER L WITH BAR
            case '\u019C': // [Latin-1 Extended-B] UPPER TURNED M
            case '\u00D1': // [Latin-1 Supplement] UPPER N WITH TILDE
            case '\u0143': // [Latin-1 Extended-A] UPPER N WITH ACUTE
            case '\u0145': // [Latin-1 Extended-A] UPPER N WITH CEDILLA
            case '\u0147': // [Latin-1 Extended-A] UPPER N WITH CARON
            case '\u019D': // [Latin-1 Extended-B] UPPER N WITH LEFT HOOK
            case '\u01F8': // [Latin-1 Extended-B] UPPER N WITH GRAVE
            case '\u00F1': // [Latin-1 Supplement] LOWER N WITH TILDE
            case '\u0144': // [Latin-1 Extended-A] LOWER N WITH ACUTE
            case '\u0146': // [Latin-1 Extended-A] LOWER N WITH CEDILLA
            case '\u0148': // [Latin-1 Extended-A] LOWER N WITH CARON
            case '\u0149': // [Latin-1 Extended-A] LOWER N PRECEDED BY APOSTROPHE
            case '\u019E': // [Latin-1 Extended-B] LOWER N WITH LONG RIGHT LEG
            case '\u01F9': // [Latin-1 Extended-B] LOWER N WITH GRAVE
            case '\u00D2': // [Latin-1 Supplement] UPPER O WITH GRAVE
            case '\u00D3': // [Latin-1 Supplement] UPPER O WITH ACUTE
            case '\u00D4': // [Latin-1 Supplement] UPPER O WITH CIRCUMFLEX
            case '\u00D5': // [Latin-1 Supplement] UPPER O WITH TILDE
            case '\u00D6': // [Latin-1 Supplement] UPPER O WITH DIAERESIS
            case '\u00D8': // [Latin-1 Supplement] UPPER O WITH STROKE
            case '\u014C': // [Latin-1 Extended-A] UPPER O WITH MACRON
            case '\u014E': // [Latin-1 Extended-A] UPPER O WITH BREVE
            case '\u0150': // [Latin-1 Extended-A] UPPER O WITH DOUBLE ACUTE
            case '\u0186': // [Latin-1 Extended-B] UPPER OPEN O
            case '\u019F': // [Latin-1 Extended-B] UPPER O WITH MIDDLE TILDE
            case '\u01A0': // [Latin-1 Extended-B] UPPER O WITH HORN
            case '\u01D1': // [Latin-1 Extended-B] UPPER O WITH CARON
            case '\u01EA': // [Latin-1 Extended-B] UPPER O WITH OGONEK
            case '\u01EC': // [Latin-1 Extended-B] UPPER O WITH OGONEK AND MACRON
            case '\u01FE': // [Latin-1 Extended-B] UPPER O WITH STROKE AND ACUTE
            case '\u020C': // [Latin-1 Extended-B] UPPER O WITH DOUBLE GRAVE
            case '\u020E': // [Latin-1 Extended-B] UPPER O WITH INVERTED BREVE
            case '\u022A': // [Latin-1 Extended-B] UPPER O WITH DIAERESIS AND MACRON
            case '\u022C': // [Latin-1 Extended-B] UPPER O WITH TILDE AND MACRON
            case '\u022E': // [Latin-1 Extended-B] UPPER O WITH DOT ABOVE
            case '\u0230': // [Latin-1 Extended-B] UPPER O WITH DOT ABOVE AND MACRON
            case '\u00F2': // [Latin-1 Supplement] LOWER O WITH GRAVE
            case '\u00F3': // [Latin-1 Supplement] LOWER O WITH ACUTE
            case '\u00F4': // [Latin-1 Supplement] LOWER O WITH CIRCUMFLEX
            case '\u00F5': // [Latin-1 Supplement] LOWER O WITH TILDE
            case '\u00F6': // [Latin-1 Supplement] LOWER O WITH DIAERESIS
            case '\u00F8': // [Latin-1 Supplement] LOWER O WITH STROKE
            case '\u014D': // [Latin-1 Extended-A] LOWER O WITH MACRON
            case '\u014F': // [Latin-1 Extended-A] LOWER O WITH BREVE
            case '\u0151': // [Latin-1 Extended-A] LOWER O WITH DOUBLE ACUTE
            case '\u01A1': // [Latin-1 Extended-B] LOWER O WITH HORN
            case '\u01D2': // [Latin-1 Extended-B] LOWER O WITH CARON
            case '\u01EB': // [Latin-1 Extended-B] LOWER O WITH OGONEK
            case '\u01ED': // [Latin-1 Extended-B] LOWER O WITH OGONEK AND MACRON
            case '\u01FF': // [Latin-1 Extended-B] LOWER O WITH STROKE AND ACUTE
            case '\u020D': // [Latin-1 Extended-B] LOWER O WITH DOUBLE GRAVE
            case '\u020F': // [Latin-1 Extended-B] LOWER O WITH INVERTED BREVE
            case '\u022B': // [Latin-1 Extended-B] LOWER O WITH DIAERESIS AND MACRON
            case '\u022D': // [Latin-1 Extended-B] LOWER O WITH TILDE AND MACRON
            case '\u022F': // [Latin-1 Extended-B] LOWER O WITH DOT ABOVE
            case '\u0231': // [Latin-1 Extended-B] LOWER O WITH DOT ABOVE AND MACRON
            case '\u01A4': // [Latin-1 Extended-B] UPPER P WITH HOOK
            case '\u01A5': // [Latin-1 Extended-B] LOWER P WITH HOOK
            case '\u0154': // [Latin-1 Extended-A] UPPER R WITH ACUTE
            case '\u0156': // [Latin-1 Extended-A] UPPER R WITH CEDILLA
            case '\u0158': // [Latin-1 Extended-A] UPPER R WITH CARON
            case '\u0210': // [Latin-1 Extended-B] UPPER R WITH DOUBLE GRAVE
            case '\u0212': // [Latin-1 Extended-B] UPPER R WITH INVERTED BREVE
            case '\u0155': // [Latin-1 Extended-A] LOWER R WITH ACUTE
            case '\u0157': // [Latin-1 Extended-A] LOWER R WITH CEDILLA
            case '\u0159': // [Latin-1 Extended-A] LOWER R WITH CARON
            case '\u0211': // [Latin-1 Extended-B] LOWER R WITH DOUBLE GRAVE
            case '\u0213': // [Latin-1 Extended-B] LOWER R WITH INVERTED BREVE
            case '\u015A': // [Latin-1 Extended-A] UPPER S WITH ACUTE
            case '\u015C': // [Latin-1 Extended-A] UPPER S WITH CIRCUMFLEX
            case '\u015E': // [Latin-1 Extended-A] UPPER S WITH CEDILLA
            case '\u0160': // [Latin-1 Extended-A] UPPER S WITH CARON
            case '\u0218': // [Latin-1 Extended-B] UPPER S WITH COMMA BELOW
            case '\u015B': // [Latin-1 Extended-A] LOWER S WITH ACUTE
            case '\u015D': // [Latin-1 Extended-A] LOWER S WITH CIRCUMFLEX
            case '\u015F': // [Latin-1 Extended-A] LOWER S WITH CEDILLA
            case '\u0161': // [Latin-1 Extended-A] LOWER S WITH CARON
            case '\u017F': // [Latin-1 Extended-A] LOWER LONG S
            case '\u0219': // [Latin-1 Extended-B] LOWER S WITH COMMA BELOW
            case '\u0162': // [Latin-1 Extended-A] UPPER T WITH CEDILLA
            case '\u0164': // [Latin-1 Extended-A] UPPER T WITH CARON
            case '\u0166': // [Latin-1 Extended-A] UPPER T WITH STROKE
            case '\u01AC': // [Latin-1 Extended-B] UPPER T WITH HOOK
            case '\u01AE': // [Latin-1 Extended-B] UPPER T WITH RETROFLEX HOOK
            case '\u021A': // [Latin-1 Extended-B] UPPER T WITH COMMA BELOW
            case '\u0163': // [Latin-1 Extended-A] LOWER T WITH CEDILLA
            case '\u0165': // [Latin-1 Extended-A] LOWER T WITH CARON
            case '\u0167': // [Latin-1 Extended-A] LOWER T WITH STROKE
            case '\u01AB': // [Latin-1 Extended-B] LOWER T WITH PALATAL HOOK
            case '\u01AD': // [Latin-1 Extended-B] LOWER T WITH HOOK
            case '\u021B': // [Latin-1 Extended-B] LOWER T WITH COMMA BELOW
            case '\u00D9': // [Latin-1 Supplement] UPPER U WITH GRAVE
            case '\u00DA': // [Latin-1 Supplement] UPPER U WITH ACUTE
            case '\u00DB': // [Latin-1 Supplement] UPPER U WITH CIRCUMFLEX
            case '\u00DC': // [Latin-1 Supplement] UPPER U WITH DIAERESIS
            case '\u0168': // [Latin-1 Extended-A] UPPER U WITH TILDE
            case '\u016A': // [Latin-1 Extended-A] UPPER U WITH MACRON
            case '\u016C': // [Latin-1 Extended-A] UPPER U WITH BREVE
            case '\u016E': // [Latin-1 Extended-A] UPPER U WITH RING ABOVE
            case '\u0170': // [Latin-1 Extended-A] UPPER U WITH DOUBLE ACUTE
            case '\u0172': // [Latin-1 Extended-A] UPPER U WITH OGONEK
            case '\u01AF': // [Latin-1 Extended-B] UPPER U WITH HORN
            case '\u01D3': // [Latin-1 Extended-B] UPPER U WITH CARON
            case '\u01D5': // [Latin-1 Extended-B] UPPER U WITH DIAERESIS AND MACRON
            case '\u01D7': // [Latin-1 Extended-B] UPPER U WITH DIAERESIS AND ACUTE
            case '\u01D9': // [Latin-1 Extended-B] UPPER U WITH DIAERESIS AND CARON
            case '\u01DB': // [Latin-1 Extended-B] UPPER U WITH DIAERESIS AND GRAVE
            case '\u0214': // [Latin-1 Extended-B] UPPER U WITH DOUBLE GRAVE
            case '\u0216': // [Latin-1 Extended-B] UPPER U WITH INVERTED BREVE
            case '\u00F9': // [Latin-1 Supplement] LOWER U WITH GRAVE
            case '\u00FA': // [Latin-1 Supplement] LOWER U WITH ACUTE
            case '\u00FB': // [Latin-1 Supplement] LOWER U WITH CIRCUMFLEX
            case '\u00FC': // [Latin-1 Supplement] LOWER U WITH DIAERESIS
            case '\u0169': // [Latin-1 Extended-A] LOWER U WITH TILDE
            case '\u016B': // [Latin-1 Extended-A] LOWER U WITH MACRON
            case '\u016D': // [Latin-1 Extended-A] LOWER U WITH BREVE
            case '\u016F': // [Latin-1 Extended-A] LOWER U WITH RING ABOVE
            case '\u0171': // [Latin-1 Extended-A] LOWER U WITH DOUBLE ACUTE
            case '\u0173': // [Latin-1 Extended-A] LOWER U WITH OGONEK
            case '\u01B0': // [Latin-1 Extended-B] LOWER U WITH HORN
            case '\u01D4': // [Latin-1 Extended-B] LOWER U WITH CARON
            case '\u01D6': // [Latin-1 Extended-B] LOWER U WITH DIAERESIS AND MACRON
            case '\u01D8': // [Latin-1 Extended-B] LOWER U WITH DIAERESIS AND ACUTE
            case '\u01DA': // [Latin-1 Extended-B] LOWER U WITH DIAERESIS AND CARON
            case '\u01DC': // [Latin-1 Extended-B] LOWER U WITH DIAERESIS AND GRAVE
            case '\u0215': // [Latin-1 Extended-B] LOWER U WITH DOUBLE GRAVE
            case '\u0217': // [Latin-1 Extended-B] LOWER U WITH INVERTED BREVE
            case '\u0174': // [Latin-1 Extended-A] UPPER W WITH CIRCUMFLEX
            case '\u0175': // [Latin-1 Extended-A] LOWER W WITH CIRCUMFLEX
            case '\u00DD': // [Latin-1 Supplement] UPPER Y WITH ACUTE
            case '\u0176': // [Latin-1 Extended-A] UPPER Y WITH CIRCUMFLEX
            case '\u0178': // [Latin-1 Extended-A] UPPER Y WITH DIAERESIS
            case '\u01B3': // [Latin-1 Extended-B] UPPER Y WITH HOOK
            case '\u0232': // [Latin-1 Extended-B] UPPER Y WITH MACRON
            case '\u00FD': // [Latin-1 Supplement] LOWER Y WITH ACUTE
            case '\u00FF': // [Latin-1 Supplement] LOWER Y WITH DIAERESIS
            case '\u0177': // [Latin-1 Extended-A] LOWER Y WITH CIRCUMFLEX
            case '\u01B4': // [Latin-1 Extended-B] LOWER Y WITH HOOK
            case '\u0233': // [Latin-1 Extended-B] LOWER Y WITH MACRON
            case '\u0179': // [Latin-1 Extended-A] UPPER Z WITH ACUTE
            case '\u017B': // [Latin-1 Extended-A] UPPER Z WITH DOT ABOVE
            case '\u017D': // [Latin-1 Extended-A] UPPER Z WITH CARON
            case '\u017A': // [Latin-1 Extended-A] LOWER Z WITH ACUTE
            case '\u017C': // [Latin-1 Extended-A] LOWER Z WITH DOT ABOVE
            case '\u017E': // [Latin-1 Extended-A] LOWER Z WITH CARON
            case '\u01B2': // [Latin-1 Extended-B] UPPER V WITH HOOK
            case '\u01B5': // [Latin-1 Extended-B] UPPER Z WITH STROKE
            case '\u0224': // [Latin-1 Extended-B] UPPER Z WITH HOOK
            case '\u01B6': // [Latin-1 Extended-B] LOWER Z WITH STROKE
            case '\u0225': // [Latin-1 Extended-B] LOWER Z WITH HOOK
                return true;
            default:
                return false;
        }
    }
}
