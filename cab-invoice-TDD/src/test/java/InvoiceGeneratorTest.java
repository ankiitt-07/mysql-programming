import com.invoice.entities.InvoiceSummary;
import com.invoice.entities.Ride;
import com.invoice.enums.RideType;
import com.invoice.repos.RideRepository;
import com.invoice.services.InvoiceGenerator;
import com.invoice.services.InvoiceService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceGeneratorTest {

    @Test
    public void givenDistanceAndTime_shouldReturnFare() {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        Ride ride = new Ride(2.0, 5, RideType.NORMAL);
        double fare = invoiceGenerator.calculateFare(ride);
        assertEquals(25.0, fare); // (2 * 10) + (5 * 1) = 25
    }

    @Test
    public void givenShortDistanceAndTime_shouldReturnMinimumFare() {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        Ride ride = new Ride(0.1, 1, RideType.NORMAL);
        double fare = invoiceGenerator.calculateFare(ride);
        assertEquals(5.0, fare); // less than min, so Rs. 5
    }

    @Test
    public void givenMultipleRides_shouldReturnTotalFare() {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        Ride[] rides = {
                new Ride(2.0, 5, RideType.NORMAL),
                new Ride(0.1, 1, RideType.NORMAL)
        };
        double fare = invoiceGenerator.calculateFare(rides);
        assertEquals(30.0, fare); // 25 + 5
    }


    @Test
    public void givenMultipleRides_shouldReturnInvoiceSummary() {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        Ride[] rides = {
                new Ride(2.0, 5, RideType.NORMAL),
                new Ride(0.1, 1, RideType.NORMAL)
        };
        InvoiceSummary summary = invoiceGenerator.calculateFareSummary(rides);
        assertEquals(2, summary.getTotalRides());
        assertEquals(30.0, summary.getTotalFare());
        assertEquals(15.0, summary.getAverageFare());
    }


    @Test
    public void givenUserId_shouldReturnInvoiceSummary() {
        RideRepository rideRepository = new RideRepository();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        InvoiceService service = new InvoiceService(rideRepository, invoiceGenerator);

        String userId = "user1";
        Ride[] rides = {
                new Ride(3.0, 10, RideType.NORMAL),
                new Ride(1.0, 2, RideType.NORMAL)
        };

        rideRepository.addRides(userId, rides);

        InvoiceSummary summary = service.getInvoiceSummary(userId);
        assertEquals(2, summary.getTotalRides());
        assertEquals(52.0, summary.getTotalFare());
    }


}