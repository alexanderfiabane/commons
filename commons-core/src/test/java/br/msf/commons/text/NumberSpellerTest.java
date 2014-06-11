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
package br.msf.commons.text;

import br.msf.commons.BaseTest;
import br.msf.commons.util.LocaleUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * @author vbox
 */
public class NumberSpellerTest extends BaseTest {

    @Test
    public void testSpellCardinal() {
        long t0 = System.currentTimeMillis();
        NumberSpeller speller = NumberSpeller.getInstance(LocaleUtils.PT_BR_LOCALE);
        int i = 0;
        assertTrue("zero".equals(speller.spellCardinal(i++)));
        assertTrue("um".equals(speller.spellCardinal(i++)));
        assertTrue("dois".equals(speller.spellCardinal(i++)));
        assertTrue("três".equals(speller.spellCardinal(i++)));
        assertTrue("quatro".equals(speller.spellCardinal(i++)));
        assertTrue("cinco".equals(speller.spellCardinal(i++)));
        assertTrue("seis".equals(speller.spellCardinal(i++)));
        assertTrue("sete".equals(speller.spellCardinal(i++)));
        assertTrue("oito".equals(speller.spellCardinal(i++)));
        assertTrue("nove".equals(speller.spellCardinal(i++)));
        assertTrue("dez".equals(speller.spellCardinal(i++)));
        assertTrue("onze".equals(speller.spellCardinal(i++)));
        assertTrue("doze".equals(speller.spellCardinal(i++)));
        assertTrue("treze".equals(speller.spellCardinal(i++)));
        assertTrue("catorze".equals(speller.spellCardinal(i++)));
        assertTrue("quinze".equals(speller.spellCardinal(i++)));
        assertTrue("dezesseis".equals(speller.spellCardinal(i++))); // ICU4J fail!
        assertTrue("dezessete".equals(speller.spellCardinal(i++))); // ICU4J fail!
        assertTrue("dezoito".equals(speller.spellCardinal(i++)));
        assertTrue("dezenove".equals(speller.spellCardinal(i++))); // ICU4J fail!
        assertTrue("vinte".equals(speller.spellCardinal(i++)));
        assertTrue("vinte e um".equals(speller.spellCardinal(i++)));
        assertTrue("vinte e dois".equals(speller.spellCardinal(i++)));
        assertTrue("vinte e três".equals(speller.spellCardinal(i++)));
        assertTrue("vinte e quatro".equals(speller.spellCardinal(i++)));
        assertTrue("vinte e cinco".equals(speller.spellCardinal(i++)));

        assertTrue("trinta e cinco".equals(speller.spellCardinal(35)));
        assertTrue("quarenta e cinco".equals(speller.spellCardinal(45)));
        assertTrue("cinquenta e cinco".equals(speller.spellCardinal(55)));
        assertTrue("sessenta e cinco".equals(speller.spellCardinal(65)));
        assertTrue("setenta e cinco".equals(speller.spellCardinal(75)));
        assertTrue("oitenta e cinco".equals(speller.spellCardinal(85)));
        assertTrue("noventa e cinco".equals(speller.spellCardinal(95)));
        assertTrue("cem".equals(speller.spellCardinal(100)));
        assertTrue("cento e cinquenta".equals(speller.spellCardinal(150)));
        assertTrue("duzentos".equals(speller.spellCardinal(200))); // ICU4J fail!
        assertTrue("trezentos".equals(speller.spellCardinal(300)));
        assertTrue("quatrocentos".equals(speller.spellCardinal(400)));
        assertTrue("quinhentos".equals(speller.spellCardinal(500)));
        assertTrue("seiscentos".equals(speller.spellCardinal(600)));
        assertTrue("setecentos".equals(speller.spellCardinal(700)));
        assertTrue("oitocentos".equals(speller.spellCardinal(800)));
        assertTrue("novecentos".equals(speller.spellCardinal(900)));
        assertTrue("mil".equals(speller.spellCardinal(1000)));

        long t1 = System.currentTimeMillis();
        infoTime("spellCardinal() tested in {0} msecs", t0, t1);
    }
}
