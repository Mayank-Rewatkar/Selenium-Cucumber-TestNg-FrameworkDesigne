package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryListener implements IRetryAnalyzer {

    int count=0;
    int max=1;

    public RetryListener() {
        System.out.println("RetryListener instantiated");
        
    }

    @Override
    public boolean retry(ITestResult iTestResult)
    {
        if(count<max)
        {
            System.out.println("Retrying " + iTestResult.getName() + " | Attempt " + (count + 1));
            count++;
            return true;
        }
        return false;
    }
}
