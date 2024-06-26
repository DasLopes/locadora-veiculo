package services;

import java.time.Duration;
import entities.CarRental;
import entities.Invoice;

public class RentalService {

    private Double pricePerHour;
    private Double pricePerDay;

    private TaxService taxService;    

    public RentalService(){

    }

    public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental carRental){
        double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
        double hours = minutes / 60.0;
        double basicPayment = (hours <= 12.0 ? pricePerHour * Math.ceil(hours) : pricePerDay * Math.ceil(hours / 24));
        double tax = taxService.tax(basicPayment);
        
        carRental.setInvoice(new Invoice(basicPayment, tax));
    }    
}
