package devon.inventorymanager.model;

/**This is the OutSourced class
 * The OutSourced class is a class that extends from the Part class
 * It represents an Outsourced part in the inventory*/
public class OutSourced extends Part{
    //The name of the company the part is outsourced from
    private String companyName;

    /**This is the constructor for the OutSourced class
     * This constructor creates a new Outsourced part
     * @param id Unique ID of the outsourced part
     * @param max Maximum inventory stock level allowed for the Outsourced part
     * @param min Minimum inventory stock level allowed for the Outsourced part
     * @param name Name of the Outsourced part
     * @param price Price of the Outsourced part
     * @param stock Current inventory stock level of the Outsourced part*/
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**This is the getCompanyName method
     * This gets the name of the company associated with the outsourced part
     * @return companyName*/
    public String getCompanyName() {
        return companyName;
    }

    /**This is the setCompanyName method
     * This method sets the name of the company associated with the outsourced part
     * @param companyName*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
