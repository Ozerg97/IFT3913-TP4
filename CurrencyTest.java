package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTest {

// Test Boite noire
    
    @Test
    void testConvertPourClasseDEquivalence() {
    	assertEquals(9000.0, currencyConverter.Currency.convert(6000.0, 1.5));
        // valeur inferieure hors intervalle
    	assertEquals(0.0, currencyConverter.Currency.convert(-50.0, 0.5));
        // valeur supperieure hors intervalle
        assertEquals(0.0, currencyConverter.Currency.convert(1000050.0, 2.2));

        
    }
    @Test
    void testConvertValeurFrontier() {
        // frontier typique inferieur
        assertEquals(0.0, currencyConverter.Currency.convert(-500.0, 1.25));
        // frontier inferieur hors intervalle 
        assertEquals(0.0, currencyConverter.Currency.convert(-1.0, 1.5));
        // frontier inferieur (montant = 0.0)
        assertEquals(0.0, currencyConverter.Currency.convert(0.0, 1.7));
        // frontier typique milieux
        assertEquals(625000.0, currencyConverter.Currency.convert(500000.0, 1.25));
        // frontier superieur (montant = 1000000.0)
        assertEquals(750000.0, currencyConverter.Currency.convert(1000000.0, 0.75));
        // frontier superieur hors intervalle
        assertEquals(0.0, currencyConverter.Currency.convert(1000001.0, 0.6));
        // frontier typique superieux
        assertEquals(0.0, currencyConverter.Currency.convert(1000500.0, 0.25));
    }
//Boite blanche
    @Test
    void testConvertPourBoiteBlanche() {
        //Couverture des instructions
        assertEquals(6250.0, currencyConverter.Currency.convert(5000.0, 1.25));
    }

}