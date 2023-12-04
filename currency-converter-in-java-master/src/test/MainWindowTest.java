package test;

import currencyConverter.MainWindow;
import currencyConverter.Currency;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

class MainWindowTest {

    private ArrayList<Currency> currencies;

    @BeforeEach
    public void init() {
        currencies = Currency.init();
    }

    // Test Boite noire
    @Test
    void testConvertPourClasseDEquivalence() {
        //Classe d'equivalence devise correct different montant
        // test avec une devise qui n'est pas dans notre liste et un montant valide
        assertEquals(93.0, currencyConverter.MainWindow.convert("USD", "EUR", currencies, 100.0));
        // Cas du montant negatif
        assertEquals(0.0, currencyConverter.MainWindow.convert("USD", "EUR", currencies, -5.0));
        // Cas du montant hors limite (out of range)
        assertEquals(0.0, currencyConverter.MainWindow.convert("USD", "EUR", currencies, 3000000.0));

        //Classe d'equivalence devise different montant valide
        //Un des devises est dans la liste
        assertEquals(0.0, currencyConverter.MainWindow.convert("USD", "CNY", currencies, 100.0));
        //Deux devises dans la liste
        assertEquals(93.0, currencyConverter.MainWindow.convert("USD", "CAD", currencies, 100.0));
        assertEquals(150.0, currencyConverter.MainWindow.convert("USD", "EUD", currencies, 100.0));
    }

    @Test
    void testConvertValeurFrontier() {
        //frontier typique inferieux intervalle
        assertEquals(0.0, currencyConverter.MainWindow.convert("USD", "EUR", currencies, -10000.0));
        // frontier inferieur hors intervalle 
        assertEquals(0.0, currencyConverter.MainWindow.convert("USD", "EUR", currencies, -1.0));
        // frontier inferieur (montant = 0.0)
        assertEquals(0.0, currencyConverter.MainWindow.convert("USD", "EUR", currencies, 0.0));
        //frontier typique milieux de l'intervalle
        assertEquals(465000.0, currencyConverter.MainWindow.convert("USD", "EUR", currencies, 500000.0));
        // frontier superieur (montant = 1000000.0)
        assertEquals(930000.0, currencyConverter.MainWindow.convert("USD", "EUR", currencies, 1000000.0));
        // frontier superieur hors intervalle
        assertEquals(0.0, currencyConverter.MainWindow.convert("USD", "EUR", currencies, 1000001.0));
        // frontier typique superieux
        assertEquals(0.0, currencyConverter.MainWindow.convert("USD", "EUR", currencies, 1010000.0));
    }

    // Test de la boite blanche
    @Test
    void testConvertPourCouverturedesinstructions() {
        assertEquals(540.0, currencyConverter.MainWindow.convert("EUR", "CHF", currencies,500.0));
    }

    @Test
    void testConvertPourCouvertureDesArcsDuGrapheDeFlotDeContrôle() {
        assertEquals(505.0, currencyConverter.MainWindow.convert("USD", "CHF", currencies, 500.0));
        assertEquals(0.0, currencyConverter.MainWindow.convert("USD", "QAR", currencies, 500.0));
        assertEquals(0.0, currencyConverter.MainWindow.convert("CFA", "GBP", currencies, 500.0));
    }

    @Test
    void testConvertPourCouvertureDesCheminsIndépendants() {
        assertEquals(1000.0, currencyConverter.MainWindow.convert("USD", "CHF", currencies, 1000.0));
        assertEquals(0.0, currencyConverter.MainWindow.convert("CHF", "RUB", currencies, 2000.0));
        assertEquals(0.0, currencyConverter.MainWindow.convert("QAR", "USD", currencies, 3000.0));
    }

    @Test
    void testConvertPourCouvertureDesI_Chemins() {
        //Pour la boucle 1
        //1 iteration
        assertEquals(906.0, currencyConverter.MainWindow.convert("GBP", "USD", currencies, 600.0));
        //2 iteration
        assertEquals(282.0, currencyConverter.MainWindow.convert("GBP", "EUR", currencies, 200.0));
        //3 iteration
        assertEquals(2840.0, currencyConverter.MainWindow.convert("EUR", "GBP", currencies, 4000.0));
        //n-1 iteration
        assertEquals(33600.0, currencyConverter.MainWindow.convert("GBP", "CNY", currencies, 3500.0));
        //n iteration
        assertEquals(778302.0, currencyConverter.MainWindow.convert("USD", "JPN", currencies, 6300.0));

        //Pour la boucle 2
        //0 iteration
        assertEquals(0.0, currencyConverter.MainWindow.convert("CHF", "CFA", currencies, 6000.0));
        //1 iteration
        assertEquals(132.0, currencyConverter.MainWindow.convert( "USD", "GBP", currencies, 200.0));
        //2 iteration
        assertEquals(400.0, currencyConverter.MainWindow.convert("EUR", "EUR", currencies, 400.0));
        //4 iteration
        assertEquals(372.0, currencyConverter.MainWindow.convert("CHF", "EUR", currencies, 400.0));
        //n-1 iteration
        assertEquals(525.0, currencyConverter.MainWindow.convert("CNY", "EUR", currencies, 3500.0));
        //n iteration
        assertEquals(50.40, currencyConverter.MainWindow.convert("JPN", "USD", currencies, 6300.0));
        
    }


    //Ici on constater que le code utilisait le nom des currency au lieu des 
    //sort name donc voici un exemple normal qui marche pour tester le code
    // assertEquals(906.0, currencyConverter.MainWindow.convert("British Pound", "US Dollar", currencies, 600.0));
}