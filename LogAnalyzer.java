/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
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
}
