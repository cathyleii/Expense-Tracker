package persistence.readers;

import model.Wallet;
import org.junit.jupiter.api.Test;
import persistence.JsonTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderWalletTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReaderWallet walletReader = new JsonReaderWallet("./data/noSuchFile.json");
        try {
            Wallet wallet = walletReader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyListNoBalanceNothingAccumulated() {
        JsonReaderWallet walletReader = new JsonReaderWallet("./data/testReaderEmptyListNoBalanceNothingAccumulated.json");
        try {
            Wallet wallet = walletReader.read();
            checkWallet("Joe", 0, 0, wallet);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyListNoBalanceSomeAccumulated() {
        JsonReaderWallet walletReader = new JsonReaderWallet("./data/testReaderEmptyListNoBalanceSomeAccumulated.json");
        try {
            Wallet wallet = walletReader.read();
            checkWallet("Joe", 100, 0, wallet);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyListSomeBalanceSomeAccumulated() {
        JsonReaderWallet walletReader = new JsonReaderWallet("./data/testReaderEmptyListSomeBalanceSomeAccumulated.json");
        try {
            Wallet wallet = walletReader.read();
            checkWallet("Joe", 200, 50, wallet);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralListNegativeBalance() {
        JsonReaderWallet walletReader = new JsonReaderWallet("./data/testReaderGeneralListNegativeBalance.json");
        try {
            Wallet wallet = walletReader.read();
            checkWallet("Jane", 95, -40, wallet);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralListNoBalance() {
        JsonReaderWallet walletReader = new JsonReaderWallet("./data/testReaderGeneralListNoBalance.json");
        try {
            Wallet wallet = walletReader.read();
            checkWallet("Jane", 100.5, 0, wallet);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralListPositiveBalance() {
        JsonReaderWallet walletReader = new JsonReaderWallet("./data/testReaderGeneralListPositiveBalance.json");
        try {
            Wallet wallet = walletReader.read();
            checkWallet("Jane", 550.55, 149.5, wallet);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
