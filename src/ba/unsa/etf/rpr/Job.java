package ba.unsa.etf.rpr;

public class Job {
    private int jobId;
    private String jobTitle;
    private int maxSalary;
    private int minSalary;

    public Job() {
    }

    public Job(int jobId, String jobTitle, int maxSalary, int minSalary) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }
}