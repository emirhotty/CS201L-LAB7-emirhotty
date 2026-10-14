
public class Pet {
    private String ID;
    private String name;
    private String dateTime;
    private String birthDate;
    private double ageAtDateTime;
    private double ageCurrent;
    private String outcomeType;
    private String type;
    private String sex;
    private String breed;

    //PRE: none
    //POST: creates an empty Pet object 
    public Pet() {
        ID = "";
        name = "";
        dateTime = "";
        birthDate = "";
        outcomeType = "";
        type = "";
        sex = "";
        breed = "";
        ageAtDateTime = 0;
        ageCurrent = 0;
    }

    //PRE: accepts information about a pet
    //POST: creates a Pet object with the given information.
    public Pet(String ID, String name, String dateTime, String birthDate,
               String outcomeType, String type, String sex, String breed) {
        this.ID = ID;

        if (name.equals(""))
            this.name = ID;
        else
            this.name = name;

        this.dateTime = dateTime;
        this.birthDate = birthDate;
        this.outcomeType = outcomeType;
        this.type = type;
        this.sex = sex;
        this.breed = breed;
    }

    //PRE: none
    //POST: returns the pet ID
    public String getID() {
        return ID;
    }

    //PRE: none
    //POST: returns the pet name
    public String getName() {
        return name;
    }

    //PRE: none
    //POST: returns the pet date and time
    public String getDateTime() {
        return dateTime;
    }

    //PRE: none
    //POST: returns the pet date of birth
    public String getBirthDate() {
        return birthDate;
    }

    //PRE: none
    //POST: returns the outcome type
    public String getOutcome() {
        return outcomeType;
    }

    //PRE: none
    //POST: returns the animal type
    public String getType() {
        return type;
    }

    //PRE: none
    //POST: returns the pet sex
    public String getSex() {
        return sex;
    }

    //PRE: none
    //POST: returns the breed
    public String getBreed() {
        return breed;
    }

    //PRE: none
    //POST: returns the age at the recorded date
    public double getAgeAtDateTime() {
        return ageAtDateTime;
    }

    //PRE: none
    //POST: returns the current age
    public double getAgeCurrent() {
        return ageCurrent;
    }

    //PRE: dateTime and birthDate contain valid dates
    //POST: calculates the pet's age at the recorded date
    public void setAgeAtDateTime() {
        int[] birth = DateUtils.parseDate(birthDate);
        int[] now = DateUtils.parseDateTime(dateTime);

        int jd1 = DateUtils.dateToJulian(birth[0], birth[1], birth[2]);
        int jd2 = DateUtils.dateToJulian(now[0], now[1], now[2]);

        int daysDiff = jd2 - jd1;
        this.ageAtDateTime = daysDiff / 365.25;
    }

    //PRE: birthDate contains a valid date
    //POST: calculates the pet's current age
    public void setAgeCurrent() {
        int[] birth = DateUtils.parseDate(birthDate);
        int[] now = DateUtils.getCurrentDate();

        int jd1 = DateUtils.dateToJulian(birth[0], birth[1], birth[2]);
        int jd2 = DateUtils.dateToJulian(now[0], now[1], now[2]);

        int daysDiff = jd2 - jd1;
        this.ageCurrent = daysDiff / 365.25;
    }

    //PRE: accepts an age
    //POST: sets the recorded age and returns it
    public double setAgeAtDateTime(double age) {
        this.ageAtDateTime = age;
        return this.ageAtDateTime;
    }

    //PRE: accepts an age
    //POST: sets the current age
    public void setAgeCurrent(double age) {
        this.ageCurrent = age;
    }

    //PRE: pet information has been loaded
    //POST: prints information about the pet 
    public void printPetInfo() {
        System.out.printf("%-22s %-10s %-35s %-18s %8.2f%n",
                name, type, breed, sex, ageAtDateTime);
    }
}
