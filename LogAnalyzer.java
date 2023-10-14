/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David Thompson
 * @version    2023.08.13
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Where to calculate the daily access counts.
    private int[] dayCounts;
    // Where to calculate the monthly access counts.
    private int[] monthCounts;
    // This will contain the name of the file to be analyzed
    private String file;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     * @param: 
     */
    public LogAnalyzer(String file)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Creat an array to hold daily access counts
        dayCounts = new int[28];
        // Create an array to hold monthly access counts
        monthCounts = new int[12];
        //Create a variable to store the name of the file
        this.file = file;
        // Create the reader to obtain the data.
        reader = new LogfileReader(file);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
        reader.reset();
    }
    
    /**
     * Analyze the daily access data from the log file.
     */
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day - 1]++;
        }
        reader.reset();
    }
    
    /**
     * Analyze the monthly access data from the log file.
     */
    public void analyzeMonthlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month - 1]++;
        }
        reader.reset();
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the daily counts.
     * These should have been set with a prior
     * call to analyzeDailyData.
     */
    public void printDailyCounts()
    {
        System.out.println("Day: Count");
        for(int day = 0; day < dayCounts.length; day++) {
            System.out.println((day + 1) + ": " + dayCounts[day]);
        }
    }
    
    /**
     * Print the monthly counts.
     * These should have been set with a prior
     * call to analyzeMonthlyData.
     */
    public void printMonthlyCounts()
    {
        System.out.println("Month: Count");
        for(int month = 0; month < monthCounts.length; month++) {
            System.out.println((month + 1) + ": " + monthCounts[month]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * Return the number of accesses recorded in the log file.
     */
    public int numberOfAccesses()
    {
        int total = 0;
        for(int hour = 0; hour < hourCounts.length; hour++) {
            total += hourCounts[hour];
        }
        return total;
    }
    
    /**
     * Return the hour with the most enteries in it.
     */
    public int busiestHour()
    {
        int largestNum = 0;
        int busiest = 0;
        for(int hour = 0; hour < hourCounts.length; hour++) {
            if(hourCounts[hour] >= largestNum){
                largestNum = hourCounts[hour];
                busiest = hour;
            }
        }
        return busiest;
    }
    
    /**
     * Return the hour with the least enteries in it.
     */
    public int quietestHour()
    {
        int smallestNum = hourCounts[0];
        int quietest = 0;
        for(int hour = 0; hour < hourCounts.length; hour++) {
            if(hourCounts[hour] <= smallestNum){
                smallestNum = hourCounts[hour];
                quietest = hour;
            }
        }
        return quietest;
    }
    
    /**
     * Return the two-hour period with the most enteries. 
     * Only the first number will be returned
     */
    public int busiestTwoHour()
    {
        int largestNum = 0;
        int busiest = 0;
        for(int hour = 0; hour < hourCounts.length - 1; hour++){
            int sum = hourCounts[hour] + hourCounts[hour + 1];
            if(sum >= largestNum){
                largestNum = sum;
                busiest = hour;
            }
        }
        return busiest;
    }
    
    /**
     * Return the hour with the most enteries in it.
     */
    public int busiestDay()
    {
        int largestNum = 0;
        int busiest = 0;
        for(int day = 0; day < dayCounts.length; day++) {
            if(dayCounts[day] >= largestNum){
                largestNum = dayCounts[day];
                busiest = day + 1;
            }
        }
        return busiest;
    }
    
    /**
     * Return the hour with the most enteries in it.
     */
    public int busiestMonth()
    {
        int largestNum = 0;
        int busiest = 0;
        for(int month = 0; month < monthCounts.length; month++) {
            if(monthCounts[month] >= largestNum){
                largestNum = monthCounts[month];
                busiest = month + 1;
            }
        }
        return busiest;
    }
    
    /**
     * Return the hour with the least enteries in it.
     */
    public int quietestDay()
    {
        int smallestNum = dayCounts[0];
        int quietest = 0;
        for(int day = 0; day < dayCounts.length; day++) {
            if(dayCounts[day] <= smallestNum){
                smallestNum = dayCounts[day];
                quietest = day + 1;
            }
        }
        return quietest;
    }
    
    /**
     * Return the hour with the least enteries in it.
     */
    public int quietestMonth()
    {
        int smallestNum = monthCounts[0];
        int quietest = 0;
        for(int month = 0; month < monthCounts.length; month++) {
            if(monthCounts[month] <= smallestNum){
                smallestNum = monthCounts[month];
                quietest = month + 1;
            }
        }
        return quietest;
    }
    
    /**
     * Takes a month and returns how many enteries that month had.
     * @param the month you want to check
     * (I have no idea if this is what you wanted this method to be,
     * since there's already a method above that shows how many 
     * access there were each month. This just seemed like the next
     * best thing to me.)
     */
    public int totalAccessesPerMonth(int month)
    {
        int enteries = monthCounts[month - 1];
        return enteries;
    }
    
    /**
     * Calculates the average access per month.
     */
    public int averageAccessesPerMonth()
    {
        int sum = 0;
        for(int month : monthCounts){
            sum += month;
        }
        int average = sum / monthCounts.length;
        return average;
    }
}
