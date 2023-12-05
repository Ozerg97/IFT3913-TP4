package test;

import currencyConverter.Currency;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(93.0, currencyConverter.MainWindow.convert("US Dollar", "Euro", currencies, 100.0));
        // Cas du montant negatif
        assertEquals(0.0, currencyConverter.MainWindow.convert("US Dollar", "Euro", currencies, -5.0));
        //Cas du montant hors limite (out of range)
        assertEquals(0.0, currencyConverter.MainWindow.convert("US Dollar", "Euro", currencies, 3000000.0));

        //Classe d'equivalence devise different montant valide
        //Un des devises est dans la liste
        //Deux devises dans la liste
        assertEquals(66.0, currencyConverter.MainWindow.convert("US Dollar", "British Pound", currencies, 100.0));
        assertEquals(0.0, currencyConverter.MainWindow.convert("US Dollar", "Lebanese Lira", currencies, 100.0));
    }

    @Test
    void testConvertValEuroFrontier() {
        //frontier typique inferieux intervalle
        assertEquals(0.0, currencyConverter.MainWindow.convert("US Dollar", "Euro", currencies, -10000.0));
        // frontier inferiEuro hors intervalle 
        assertEquals(0.0, currencyConverter.MainWindow.convert("US Dollar", "Euro", currencies, -1.0));
        // frontier inferiEuro (montant = 0.0)
        assertEquals(0.0, currencyConverter.MainWindow.convert("US Dollar", "Euro", currencies, 0.0));
        //frontier typique milieux de l'intervalle
        assertEquals(465000.0, currencyConverter.MainWindow.convert("US Dollar", "Euro", currencies, 500000.0));
        // frontier superiEuro (montant = 1000000.0)
        assertEquals(930000.0, currencyConverter.MainWindow.convert("US Dollar", "Euro", currencies, 1000000.0));
        // frontier superiEuro hors intervalle
        assertEquals(0.0, currencyConverter.MainWindow.convert("US Dollar", "Euro", currencies, 1000001.0));
        // frontier typique superieux
        assertEquals(0.0, currencyConverter.MainWindow.convert("US Dollar", "Euro", currencies, 1010000.0));
    }

    // Test de la boite blanche
    @Test
    void testConvertPourCouverturedesinstructions() {
        assertEquals(540.0, currencyConverter.MainWindow.convert("Euro", "Swiss Franc", currencies,500.0));
    }

    @Test
    void testConvertPourCouvertureDesArcsDuGrapheDeFlotDeContrôle() {
        assertEquals(505.0, currencyConverter.MainWindow.convert("US Dollar", "Swiss Franc", currencies, 500.0));
        assertEquals(0.0, currencyConverter.MainWindow.convert("US Dollar", "QAR", currencies, 500.0));
        assertEquals(0.0, currencyConverter.MainWindow.convert("CFA", "British Pound", currencies, 500.0));
    }

    @Test
    void testConvertPourCouvertureDesCheminsIndépendants() {
        assertEquals(1010.0, currencyConverter.MainWindow.convert("US Dollar", "Swiss Franc", currencies, 1000.0));
        assertEquals(0.0, currencyConverter.MainWindow.convert("Swiss Franc", "RUB", currencies, 2000.0));
        assertEquals(0.0, currencyConverter.MainWindow.convert("QAR", "US Dollar", currencies, 3000.0));
    }

    @Test
    void testConvertPourCouvertureDesI_Chemins() {
        //Pour la boucle 1
        //1 iteration
        assertEquals(906.0, currencyConverter.MainWindow.convert("British Pound", "US Dollar", currencies, 600.0));
        //2 iteration
        assertEquals(282.0, currencyConverter.MainWindow.convert("British Pound", "Euro", currencies, 200.0));
        //3 iteration
        assertEquals(2840.0, currencyConverter.MainWindow.convert("Euro", "British Pound", currencies, 4000.0));
        //n-1 iteration
        assertEquals(33600.0, currencyConverter.MainWindow.convert("British Pound", "Chinese Yuan Renminbi", currencies, 3500.0));
        //n iteration
        assertEquals(778302.0, currencyConverter.MainWindow.convert("US Dollar", "Japanese Yen", currencies, 6300.0));

        //Pour la boucle 2
        //0 iteration
        assertEquals(0.0, currencyConverter.MainWindow.convert("Swiss Franc", "CFA", currencies, 6000.0));
        //1 iteration
        assertEquals(132.0, currencyConverter.MainWindow.convert( "US Dollar", "British Pound", currencies, 200.0));
        //2 iteration
        assertEquals(400.0, currencyConverter.MainWindow.convert("Euro", "Euro", currencies, 400.0));
        //4 iteration
        assertEquals(372.0, currencyConverter.MainWindow.convert("Swiss Franc", "Euro", currencies, 400.0));
        //n-1 iteration
        assertEquals(525.0, currencyConverter.MainWindow.convert("Chinese Yuan Renminbi", "Euro", currencies, 3500.0));
        //n iteration
        assertEquals(50.40, currencyConverter.MainWindow.convert("Japanese Yen", "US Dollar", currencies, 6300.0));
        
    }
}