import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//make the car class. the car contains the id, brand name, model name, price/d, and if it is available or not.
class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) { //constructor
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;                    //initially the car will be available. therefore, we assign the value true.
    }
    public String getCarId() {                      //getter
        return carId;
    }

    public String getBrand() {                      //getter
        return brand;
    }

    public String getModel() {                      //getter
        return model;
    }

    public double calculatePrice(int rentalDays) {  //calculate the price required for the days requested
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {                  //available or not
        return isAvailable;
    }

    public void rent() {                            //the car is rented and the value is not false
        isAvailable = false;
    }

    public void returnCar() {                       //the car is returned and the value is now true
        isAvailable = true;
    }
}

//make the customer class. customers have their name and id
class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {       //constructor
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {                         //getter
        return customerId;
    }

    public String getName() {                               //getter
        return name;
    }
}

//class for rented car. contains the car, the customer who rented it. the number of days of rent.
class Rental {
    private Car car;                                        //object of type Car
    private Customer customer;                              //object of type Customer
    private int days;                                       //number of days of rent

    public Rental(Car car, Customer customer, int days) {   //constructor
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {                                   //getter
        return car;
    }

    public Customer getCustomer() {                         //getter
        return customer;
    }

    public int getDays() {                                  //getter
        return days;
    }
}

//class that stores the details of all the cars in the store in arraylists
class CarRentalSystem {
    private List<Car> cars;                                 //ArrayList of objects of class: Car
    private List<Customer> customers;                       //ArrayList of objects of class: Customer
    private List<Rental> rentals;                           //ArrayList of objects of class: Rental

    public CarRentalSystem() {                              //constructor
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {                           //add a car that in the store
        cars.add(car);
    }

    public void addCustomer(Customer customer) {            //add a customer
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) { //function to rent a car. car, customer, days are provided
        if (car.isAvailable()) {                            //if the car is available then it is rented
            car.rent();
            rentals.add(new Rental(car, customer, days));

        } else {                                            //else print that the car is not available for rented
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {                        //function to return the car
        car.returnCar();                                    //the isAvailable is set to true through this function
        Rental rentalToRemove = null;                       //initialize an object of type Rental with null
        for (Rental rental : rentals) {                     //run a for loop through the arraylist: rentals. this is to find if the car was actually rented
            if (rental.getCar() == car) {                   //if the car is found to be rented, it is stored in the object rentalToRental
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {                       //if the car was found remove it from the rentals arraylist of objects
            rentals.remove(rentalToRemove);

        } else {                                            //else print that the car was not rented
            System.out.println("Car was not rented.");
        }
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------
    //MENU
    // Interface for the Car Rental System
    public void menu() {
        Scanner scanner = new Scanner(System.in);

        //make the switch case menu
        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");        //the user enters her choice

            int choice = scanner.nextInt();
            scanner.nextLine();                             // Consume newline

            //USER SELECTS TO RENT A CAR------------------------------------------------------------------------------------------------------------------
            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");      //customer enters her name
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");    //all available cars are listed for the customer
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel()); //id, brand and mode of the cars are listed one by one
                    }
                }

                System.out.print("\nEnter the car ID you want to rent: ");      //get the car id that the customer wants to rent
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");      //get the number of days that the customer wants to rent the car for
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName); //the customer id is initialized with CUS
                addCustomer(newCustomer);                                       //add the customer to the arraylist of customers

                Car selectedCar = null;                                         //a new Car object is initialized with null
                for (Car car : cars) {                                          //we find this car from the arraylist of the cars
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;                                      //if the car is found, we store it in the new object
                        break;
                    }
                }
                //if the car is not found then the object remains null

                if (selectedCar != null) {                                      //if the car is found
                    double totalPrice = selectedCar.calculatePrice(rentalDays);             //get the total price of the rental
                    System.out.println("\n== Rental Information ==\n");                     //print the information for the customer
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: Rs. %.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");                           //customer will confirm if she wants to rent the car
                    String confirm = scanner.nextLine();

                    //perform as per confirmation
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            }

            //USER SELECTS TO RETURN A CAR------------------------------------------------------------------------------------------------------------------
            else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");  //the customer enters the car id she wants to return
                String carId = scanner.nextLine();

                Car carToReturn = null;                                     //a new Car object is initialized with null
                for (Car car : cars) {                                      //searches for the car
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;                                  //if the car is found, we store it in the new object
                        break;
                    }
                }

                if (carToReturn != null) {                                  //if the car is found
                    Customer customer = null;                               //a new Customer object is initialized with null
                    for (Rental rental : rentals) {                         //find the car from the rentals arraylist
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();                //get the customer details
                            break;
                        }
                    }

                    if (customer != null) {                                 //if the Customer is found
                        returnCar(carToReturn);                             //return the car
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            }
            //USER CHOOSES TO EXIT----------------------------------------------------------------------------------------------------------------------
            else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
    }

}
public class Main{
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Hyundai", "IONIQ5", 11000.0); // Different base price per day for each car
        Car car2 = new Car("C002", "Mahindra", "Thar", 15000.0);
        Car car3 = new Car("C003", "Audi", "RSQ8", 18000.0);
        Car car4 = new Car("C004", "Porsche", "911", 22000.0);
        Car car5 = new Car("C005", "BMW", "XM", 25000.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);


        rentalSystem.menu();
    }
}
