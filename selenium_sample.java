package allSteps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDef {
	
	
	WebElement oWE;
	ArrayList<String> tabs;
	String ListName;
	String Screening;
	String BusinessUnit;
	String BusinessUnitContact ;
	String Jurisdiction;
	String Remarks;
	String ListType;
	
	String Title;
	String FirstName;
	String LastName;
	String OrginatingBU;
	String BUContact;
	String StartDate;
	String CustomerStatus;
	String ReviewPeriod;
	//String Jurisdiction;
	String Why;
	String What;
	String Comments;
	static String PreviousListName= "Sample159144";
	
	@Given("^I have an application$")
	public void i_have_an_application(DataTable arg1) throws Throwable {		
		String url = arg1.asMaps(String.class, String.class).get(0).get("URL");
		cp.driver.get(url);
		
	    //throw new PendingException();
	}

	@When("^I Enter login details$")
	public void i_Enter_login_details(DataTable arg1) throws Throwable {
		String username = arg1.asMaps(String.class, String.class).get(0).get("Username");
		String pass = arg1.asMaps(String.class, String.class).get(0).get("Password");
		
		oWE=cp.driver.findElement(By.name("username"));
		oWE.sendKeys(username);
		oWE=cp.driver.findElement(By.name("j_password"));
		oWE.sendKeys(pass);
		oWE.submit();	
	    //throw new PendingException();
	}

	@Then("^I should be able to login$")
	public void i_should_be_able_to_login() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}
	
	@Given("^I have logged in to application$")
	public void i_have_logged_in_to_application() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@When("^i click on AddList Entry tab$")
	public void i_click_on_AddList_Entry_tab() throws Throwable {
		oWE=cp.driver.findElement(By.xpath(".//*[@id='disp-navigation']/li[1]/a"));
		oWE.click();
	    //throw new PendingException();
	}

	@Then("^New tab opens$")
	public void new_tab_opens() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@Then("^Select Private List from the drop down and Submit$")
	public void select_Private_List_from_the_drop_down_and_Submit() throws Throwable {
		
		cp.parentWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
		
		//tabs = new ArrayList<String> (cp.driver.getWindowHandles());
		//cp.driver.switchTo().window(tabs.get(1));
		
		Select dropdown = new Select(cp.driver.findElement(By.xpath(".//*[@id='ComboBox0']/select")));
		dropdown.selectByVisibleText("Private List");
		Thread.sleep(1000);
		oWE=cp.driver.findElement(By.id("ButtonGroup0_Button0"));		//*[@id="ButtonGroup0_Button0"]/span
		oWE.click();
	    //throw new PendingException();
	}

	@Then("^Enter all mandatory fields$")
	public void enter_all_mandatory_fields(DataTable arg1) throws Throwable {

		Random rand = new Random();
		int randomNum = rand.nextInt(100001) + 100000;
		ListName= arg1.asMaps(String.class, String.class).get(0).get("ListName") + randomNum;
		System.out.println(ListName);
		Screening = arg1.asMaps(String.class, String.class).get(0).get("Screening");
		BusinessUnit = arg1.asMaps(String.class, String.class).get(0).get("BusinessUnit");
		BusinessUnitContact = arg1.asMaps(String.class, String.class).get(0).get("BusinessUnitContact");
		Jurisdiction = arg1.asMaps(String.class, String.class).get(0).get("Jurisdiction");
		Remarks = arg1.asMaps(String.class, String.class).get(0).get("Remarks");
		ListType = arg1.asMaps(String.class, String.class).get(0).get("ListType");
		
		oWE=cp.driver.findElement(By.name("tw#local#privateList#name"));		//*[@id="InputText3"]/input
		oWE.sendKeys(ListName);
		
		Select dropdown = new Select(cp.driver.findElement(By.name("tw#local#privateList#privateListTypeInt")));
		dropdown.selectByVisibleText(ListType);
		
		oWE=cp.driver.findElement(By.id("131"));
		oWE.click();
		
		oWE=cp.driver.findElement(By.xpath("//*[@id='multipleSelect']/option[9]"));	
		oWE.click();
		
		oWE=cp.driver.findElement(By.id("InputText23"));	  //*[@id="InputText23"]	//*[@id="InputText23"]
		oWE.sendKeys(BusinessUnitContact);
		
		oWE=cp.driver.findElement(By.id("15"));
		oWE.click();
		
		//Select dropdown = new Select(cp.driver.findElement(By.name("tw#local#privateList#jurisdictionInt")));
		//dropdown.selectByIndex(1);
		
		//oWE=cp.driver.findElement(By.name("tw#local#privateList#jurisdictionInt"));
		//oWE.sendKeys("UAE");
		
		oWE=cp.driver.findElement(By.name("tw#local#privateList#remarks"));		//*[@id="InputText12"]/textarea
		oWE.sendKeys(Remarks);
		
	    //throw new PendingException();
	}

	@Then("^click on Add Private List$")
	public void click_on_Add_Private_List() throws Throwable {
	    
		oWE=cp.driver.findElement(By.id("ButtonGroup3_Button0"));
		oWE.click();
		oWE=cp.driver.findElement(By.id("ButtonGroup0_Button0"));
		oWE.click();
	    
	}

	@Then("^new list confirmation page opens and new case is created\\.$")
	public void new_list_confirmation_page_opens_and_new_case_is_created() throws Throwable {
	    
		String parentWindowHandler = cp.driver.getWindowHandle();
		
        String subWindowHandler = null;

        Set<String> handles = cp.driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        
        cp.driver.switchTo().window(subWindowHandler);

        oWE=cp.driver.findElement(By.xpath("//*[@id='CustomHTML2']/b/h3/font"));
        cp.caseReference=oWE.getText();
        
        System.out.println(cp.caseReference);
        
		oWE=cp.driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/button/span"));
		oWE.click();
		
		try{
			cp.driver.switchTo().window(parentWindowHandler);
			cp.driver.close();
		}
		catch (NoSuchWindowException e) {
			cp.driver.switchTo().window(cp.secondWindowHandler);
			cp.driver.close();
		}
		
		cp.driver.switchTo().window(cp.parentWindowHandler);

	}
	
	@Then("^first user logs-off and another user logs in$")
	public void first_user_logs_off_and_another_user_logs_in() throws Throwable {
		
		oWE=cp.driver.findElement(By.cssSelector("a[title='Log-Off']"));
		oWE.click();
	}
	
	@Then("^I search case reference$")
	public void i_search_case_reference() throws Throwable {
		
		WebDriverWait wait = new WebDriverWait(cp.driver, 10);
		//wait.until(ExpectedConditions.
		
		for(int i=1;i<=3;i++){
			try{
				wait.until( ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='gs_caseRef']")));
		
			}catch (TimeoutException e) {
				WebElement refreshTaskListButton=cp.driver.findElement(By.xpath(".//*[@id='ButtonGroup0_Button0']"));
				refreshTaskListButton.click();
				//System.out.println(e);
			}
		}
		String taskListSearchXpath="//*[@id='gs_caseRef']";
		oWE=cp.driver.findElement(By.xpath(taskListSearchXpath));
		oWE.sendKeys(cp.caseReference + "\n");
	}

	@Then("^validate the task list details in task list$")
	public void validate_the_task_list_details_in_task_list() throws Throwable {
		
		WebDriverWait waitnew = new WebDriverWait(cp.driver, 10);
		for(int i=1;i<=2;i++){
			try{
				waitnew.until( ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby = 'TaskTable_taskId']")));
		
			}catch (TimeoutException e) {
				WebElement refreshTaskListButton=cp.driver.findElement(By.xpath(".//*[@id='ButtonGroup0_Button0']"));
				refreshTaskListButton.click();
				String taskListSearchXpath="//*[@id='gs_caseRef']";
				oWE=cp.driver.findElement(By.xpath(taskListSearchXpath));
				oWE.sendKeys(cp.caseReference + "\n");
			}
		}
		
		String createdDateTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_creationDate']")).getText();
		String listNameTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_name']")).getText();
		String screeningTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_screening']")).getText();
		String ownerTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_owner']")).getText();
		String jurisdictionTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_jurisdiction']")).getText();
		String businessUnitTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_businessUnit']")).getText();
		String caseTypeTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_caseType']")).getText();
		String caseStatusTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_caseStatuses']")).getText();
		String assignedToTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_assignedTo']")).getText();
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();	
		String currentDate= dateFormat.format(date);
		
		//System.out.println(cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_name']")).getText());
		org.junit.Assert.assertTrue(currentDate.equalsIgnoreCase(createdDateTaskList));
		org.junit.Assert.assertTrue(ListName.equalsIgnoreCase(listNameTaskList));
		org.junit.Assert.assertTrue(Screening.equalsIgnoreCase(screeningTaskList));
		org.junit.Assert.assertTrue(BusinessUnit.equalsIgnoreCase(businessUnitTaskList));
		org.junit.Assert.assertTrue(Jurisdiction.equalsIgnoreCase(jurisdictionTaskList));
		org.junit.Assert.assertTrue("Private List".equalsIgnoreCase(caseTypeTaskList));
		org.junit.Assert.assertTrue("Awaiting 4 Eye Approval".equalsIgnoreCase(caseStatusTaskList));
		//org.junit.Assert.assertTrue("My Case".equalsIgnoreCase(assignedToTaskList));
	}

	@Then("^click on Run button$")
	public void click_on_Run_button() throws Throwable {
		
		WebDriverWait waitnew = new WebDriverWait(cp.driver, 10);
		for(int i=1;i<=2;i++){
			try{
				waitnew.until( ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby = 'TaskTable_taskId']")));
		
			}catch (TimeoutException e) {
				WebElement refreshTaskListButton=cp.driver.findElement(By.xpath(".//*[@id='ButtonGroup0_Button0']"));
				refreshTaskListButton.click();
				String taskListSearchXpath="//*[@id='gs_caseRef']";
				oWE=cp.driver.findElement(By.xpath(taskListSearchXpath));
				oWE.sendKeys(cp.caseReference + "\n");
			}
		}
		
		WebElement runLinkTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_taskId']"));
		runLinkTaskList.click();
		
		/*if(ExpectedConditions.alertIsPresent() != null){
			Alert alert = cp.driver.switchTo().alert();
			alert.accept();
			Thread.sleep(1000);
		}*/
		cp.parentWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
		
		
		
	}

	@Then("^I search case reference on \"([^\"]*)\" task list$")
	public void i_search_case_reference_on_task_list(String arg1) throws Throwable {
		WebElement publicTaskListTable=cp.driver.findElement(By.id("section4"));
		
		
		WebDriverWait wait = new WebDriverWait(cp.driver, 10);
		//wait.until(ExpectedConditions.
		
		for(int i=1;i<=3;i++){
			try{
				wait.until( ExpectedConditions.elementToBeClickable(publicTaskListTable.findElement(By.xpath(".//*[@id='gs_caseRef']"))));
		
			}catch (TimeoutException e) {
				WebElement refreshTaskListButton=cp.driver.findElement(By.xpath(".//*[@id='ButtonGroup0_Button0']"));
				refreshTaskListButton.click();
				//System.out.println(e);
			}
		}
		String taskListSearchXpath=".//*[@id='gs_caseRef']";
		oWE=publicTaskListTable.findElement(By.xpath(taskListSearchXpath));
		oWE.sendKeys(cp.caseReference + "\n");
	}

	@Then("^click on Run button on \"([^\"]*)\" task list$")
	public void click_on_Run_button_on_task_list(String arg1) throws Throwable {
		
		WebElement publicTaskListTable=cp.driver.findElement(By.id("section4"));
		WebDriverWait waitnew = new WebDriverWait(cp.driver, 10);
		for(int i=1;i<=2;i++){
			try{
				waitnew.until( ExpectedConditions.elementToBeClickable(publicTaskListTable.findElement(By.cssSelector("td[aria-describedby = 'TaskTablePublic_taskId']"))));
		
			}catch (TimeoutException e) {
				WebElement refreshTaskListButton=cp.driver.findElement(By.xpath(".//*[@id='ButtonGroup0_Button0']"));
				refreshTaskListButton.click();
				String taskListSearchXpath=".//*[@id='gs_caseRef']";
				oWE=publicTaskListTable.findElement(By.xpath(taskListSearchXpath));
				oWE.sendKeys(cp.caseReference + "\n");
			}
		}
		
		WebElement runLinkTaskList=publicTaskListTable.findElement(By.cssSelector("td[aria-describedby = 'TaskTablePublic_taskId']"));
		runLinkTaskList.click();
		
		/*if(ExpectedConditions.alertIsPresent() != null){
			Alert alert = cp.driver.switchTo().alert();
			alert.accept();
			Thread.sleep(1000);
		}*/
		
		cp.parentWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
	}
	@Then("^validate task list details in Run Page$")
	public void validate_task_list_details_in_Run_Page() throws Throwable {
		String caseRefRunWindow=cp.driver.findElement(By.xpath("//*[@id='CustomHTML3']/h2/font")).getText();	
		System.out.println(caseRefRunWindow);
		
		String listNameRunWindow=cp.driver.findElement(By.xpath(".//*[@id='Label0']/span")).getText();	
		String screeningRunWindow=cp.driver.findElement(By.xpath(".//*[@id='Label20']/span")).getText();
		String businessUnitRunWindow=cp.driver.findElement(By.xpath(".//*[@id='Label21']/span")).getText();
		String businessUnitContactRunWindow=cp.driver.findElement(By.xpath(".//*[@id='Label22']/span")).getText();
		String jurisdictionRunWindow=cp.driver.findElement(By.xpath(".//*[@id='Label23']/span")).getText();	
		String listTypeRunWindow=cp.driver.findElement(By.xpath("//*[@id='InputText0']/span")).getText();
		
		org.junit.Assert.assertTrue(ListName.equalsIgnoreCase(listNameRunWindow));
		org.junit.Assert.assertTrue(Screening.equalsIgnoreCase(screeningRunWindow));
		org.junit.Assert.assertTrue(BusinessUnit.equalsIgnoreCase(businessUnitRunWindow));
		org.junit.Assert.assertTrue(Jurisdiction.equalsIgnoreCase(jurisdictionRunWindow));
		org.junit.Assert.assertTrue(BusinessUnitContact.equalsIgnoreCase(businessUnitContactRunWindow));
		org.junit.Assert.assertTrue(ListType.equalsIgnoreCase(listTypeRunWindow));
		org.junit.Assert.assertTrue(caseRefRunWindow.equalsIgnoreCase(cp.caseReference));
	}

	@Then("^approve the case$")
	public void approve_the_case() throws Throwable {
		
		oWE=cp.driver.findElement(By.id("managerDecision_Approve"));	
		oWE.click();
		
		String parentWindowHandler = cp.driver.getWindowHandle();
        String subWindowHandler = null;
        Set<String> handles = cp.driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        
        cp.driver.switchTo().window(subWindowHandler);
        
        //code here
        WebElement approveButton=cp.driver.findElement(By.xpath("html/body/div[5]/div[3]/div/button[1]"));	
        approveButton.click();
		//cp.driver.switchTo().window(parentWindowHandler);
		cp.driver.switchTo().window(cp.parentWindowHandler);
		
	}
	
	@Then("^click on search button$")
	public void click_on_search_button() throws Throwable {
		WebElement searchButton=cp.driver.findElement(By.xpath("//*[@id='disp-navigation']/li[3]/a"));
		searchButton.click();
		
		cp.parentWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
		
		WebElement caseIDSearchPage=cp.driver.findElement(By.xpath("//*[@id='InputText0']/input"));
		caseIDSearchPage.sendKeys(cp.caseReference);
		WebElement searchButtonSearchPage=cp.driver.findElement(By.id("ButtonGroup0_Button0"));
		searchButtonSearchPage.click();
	}

	@Then("^validate the status of the case$")
	public void validate_the_status_of_the_case() throws Throwable {
		WebElement caseStatus=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'searchCaseTable_status']"));
		String caseStatusSearchPage=caseStatus.getText();
		if(!caseStatusSearchPage.equalsIgnoreCase("Close")){
			Thread.sleep(10000);
			WebElement searchButtonSearchPage=cp.driver.findElement(By.id("ButtonGroup0_Button0"));
			searchButtonSearchPage.click();
		}
		caseStatus=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'searchCaseTable_status']"));
		caseStatusSearchPage=caseStatus.getText();
        org.junit.Assert.assertTrue("Close".equalsIgnoreCase(caseStatusSearchPage));
        cp.driver.close();
        cp.driver.switchTo().window(cp.parentWindowHandler);
        cp.driver.close();
	}

	@Then("^reject the case$")
	public void reject_the_case() throws Throwable {
		oWE=cp.driver.findElement(By.id("managerDecision_Reject"));
		oWE.click();
		
		String parentWindowHandler = cp.driver.getWindowHandle();
        String subWindowHandler = null;
        Set<String> handles = cp.driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        
        cp.driver.switchTo().window(subWindowHandler);
        
        WebElement rejectComments=cp.driver.findElement(By.xpath(".//*[@id='InputText8']/textarea"));	//*[@id="InputText14"]/textarea
        rejectComments.sendKeys("Rejected");
        WebElement rejectButton=cp.driver.findElement(By.xpath("html/body/div[4]/div[3]/div/button[1]"));
        rejectButton.click();
		cp.driver.switchTo().window(cp.parentWindowHandler);
	}

	@When("^validate if the case is assigned to creator$")
	public void validate_if_the_case_is_assigned_to_creator() throws Throwable {
		
		WebDriverWait waitnew = new WebDriverWait(cp.driver, 10);
		for(int i=1;i<=2;i++){
			try{
				waitnew.until( ExpectedConditions.elementToBeClickable(By.cssSelector("td[aria-describedby = 'TaskTable_taskId']")));
		
			}catch (TimeoutException e) {
				WebElement refreshTaskListButton=cp.driver.findElement(By.xpath(".//*[@id='ButtonGroup0_Button0']"));
				refreshTaskListButton.click();
				String taskListSearchXpath="//*[@id='gs_caseRef']";
				oWE=cp.driver.findElement(By.xpath(taskListSearchXpath));
				oWE.sendKeys(cp.caseReference + "\n");
			}
		}
		String assignedToTaskList=cp.driver.findElement(By.cssSelector("td[aria-describedby = 'TaskTable_assignedTo']")).getText();
		org.junit.Assert.assertTrue("My Case".equalsIgnoreCase(assignedToTaskList));
	}
	
	@When("^click \"(.*?)\" for the case on Run Page$")
	public void click_for_the_case_on_Run_Page(String arg1) throws Throwable {

		WebElement actionButton=cp.driver.findElement(By.xpath("//span[contains(text(),'" +  arg1 + "')]/parent::button"));	
		Actions actions = new Actions(cp.driver);
		actions.moveToElement(actionButton).click().perform();
		
		cp.driver.switchTo().window(cp.parentWindowHandler);
	}
	
	@When("^i click on Edit or Delete Entry List$")
	public void i_click_on_Edit_or_Delete_Entry_List() throws Throwable {

		WebElement editAndDeleteLink=cp.driver.findElement(By.xpath("//*[@id='disp-navigation']/li[2]/a"));
		editAndDeleteLink.click();
	}

	@Then("^click on search on search list and entry page$")
	public void click_on_search_on_search_list_and_entry_page() throws Throwable {

		WebElement searchButton=cp.driver.findElement(By.id("ButtonGroup0_Button0"));
		searchButton.click();
	}

	@Then("^click on first entry type from the list on search list and entry page$")
	public void click_on_first_entry_type_from_the_list_on_search_list_and_entry_page() throws Throwable {
		
		WebElement listLink=cp.driver.findElement(By.xpath("//*[@id='1']/td[1]/a"));
		listLink.click();
		cp.secondWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
		
		
	}

	@Then("^click on Edit private record on Edit Private List Record page$")
	public void click_on_Edit_private_record_on_Edit_Private_List_Record_page() throws Throwable {
	   
		WebElement editPrivateRecordButton=cp.driver.findElement(By.id("ButtonGroup3_Button0"));
		editPrivateRecordButton.click();
	}

	@Then("^click submit on Edit Private List Confirmation page$")
	public void click_submit_on_Edit_Private_List_Confirmation_page() throws Throwable {
		
		WebElement submitButton=cp.driver.findElement(By.id("ButtonGroup0_Button0"));
		submitButton.click();
	}
	
	@When("^select Type and Business Unit$")
	public void select_Type_and_Business_Unit() throws Throwable {
		cp.parentWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
		
		Select Typedropdown = new Select(cp.driver.findElement(By.name("tw#local#listType")));
		Typedropdown.selectByVisibleText("Private List");
		
		//WebElement listName=cp.driver.findElement(By.xpath("//*[@id='InputText3']/input"));
		//listName.sendKeys(ListName);
		
		WebElement businessUnit=cp.driver.findElement(By.xpath("//*[@id='multipleSelect']/option[2]"));
		businessUnit.click();
		
	}
	
	@When("^select Type and enter list name in search list and entry page for \"(.*?)\"$")
	public void select_Type_and_enter_list_name_in_search_list_and_entry_page_for(String arg1) throws Throwable {
		
		if (arg1.equalsIgnoreCase("Edit")){
			ListName= PreviousListName;
		}
		cp.parentWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
		
		Select Typedropdown = new Select(cp.driver.findElement(By.name("tw#local#listType")));
		Typedropdown.selectByVisibleText("Private List");
		
		WebElement listName=cp.driver.findElement(By.xpath("//*[@id='InputText3']/input"));
		listName.sendKeys(ListName);
		Thread.sleep(5000);
	}

	@When("^click \"(.*?)\" on list/entry details page$")
	public void click_on_list_entry_details_page(String arg1) throws Throwable {
		//WebElement editdeleteButton = null;
		
		//if(arg1.equalsIgnoreCase("Edit")){
		//	editdeleteButton=cp.driver.findElement(By.id("managerDecision_Approve"));
			
		//}else if(arg1.equalsIgnoreCase("Delete")){
		//	editdeleteButton=cp.driver.findElement(By.id("managerDecision_Reject"));
		//}
		  
		//editdeleteButton.click();
		if(arg1.equalsIgnoreCase("Suppress")){
			//*[@id="managerDecision_Button2"]/span
			WebElement actionButton=cp.driver.findElement(By.id("managerDecision_Button2"));	//html/body/div[6]/div[3]/div/button[1]/span
			actionButton.click();																//html/body/div[7]/div[3]/div/button[1]/span
			return;
		}
		
		
		WebElement actionButton=cp.driver.findElement(By.xpath("//span[contains(text(),'" + arg1 + "')]/parent::button"));
		Actions actions = new Actions(cp.driver);
		actions.moveToElement(actionButton).click().perform();
	
	}
	
	@When("^edit fields on edit private list page$")
	public void edit_fields_on_edit_private_list_page(DataTable arg1) throws Throwable {

		Random rand = new Random();
		int randomNum = rand.nextInt(100001) + 100000;
		ListName= arg1.asMaps(String.class, String.class).get(0).get("ListName") + randomNum;
		Screening = arg1.asMaps(String.class, String.class).get(0).get("Screening");
		BusinessUnit = arg1.asMaps(String.class, String.class).get(0).get("BusinessUnit");
		BusinessUnitContact = arg1.asMaps(String.class, String.class).get(0).get("BusinessUnitContact");
		Jurisdiction = arg1.asMaps(String.class, String.class).get(0).get("Jurisdiction");
		Remarks = arg1.asMaps(String.class, String.class).get(0).get("Remarks");
		ListType = arg1.asMaps(String.class, String.class).get(0).get("ListType");
		
		//WebElement oListName=cp.driver.findElement(By.name("tw#local#privateListInfo#name"));
		//oListName.clear();
		//oListName.sendKeys(ListName);
		
		Select dropdown = new Select(cp.driver.findElement(By.name("tw#local#privateListInfo#privateListTypeInt")));
		dropdown.selectByVisibleText(ListType);
		
		WebElement oScreening=cp.driver.findElement(By.name("194"));	
		oScreening.click();
		
		WebElement oBusinessUnit=cp.driver.findElement(By.xpath("//*[@id='multipleSelect']/option[9]"));	
		oBusinessUnit.click();
		
		WebElement oBusinessUnitContact=cp.driver.findElement(By.name("tw#local#privateListInfo#owner"));	  //*[@id="InputText23"]	//*[@id="InputText23"]
		oBusinessUnitContact.sendKeys(BusinessUnitContact);
		
		WebElement oJurisdiction=cp.driver.findElement(By.name("25"));
		oJurisdiction.click();
		
		WebElement oRemarks=cp.driver.findElement(By.name("tw#local#privateListInfo#remarks"));		//*[@id="InputText12"]/textarea
		oRemarks.sendKeys(Remarks);
	}

	@When("^click \"(.*?)\" on add/edit private list page$")
	public void click_on_add_edit_private_list_page(String arg1) throws Throwable {

		WebElement confButton=cp.driver.findElement(By.id("ButtonGroup3_Button0"));
		confButton.click();
	}
	
	@When("^click \"(.*?)\" on add/edit private list confirmation page$")
	public void click_on_add_edit_private_list_confirmation_page(String arg1) throws Throwable {

		WebElement confButton=cp.driver.findElement(By.id("ButtonGroup0_Button0"));
		confButton.click();
	}

	@Then("^click on \"(.*?)\" on approval page$")
	public void click_on_on_approval_page(String arg1) throws Throwable {

		WebElement approveRejectButton=cp.driver.findElement(By.xpath("//span[contains(text(),'" +  arg1 + "')]/parent::button"));	
		Actions actions = new Actions(cp.driver);
		actions.moveToElement(approveRejectButton).click().perform();
		//WebElement approveButton=cp.driver.findElement(By.id("managerDecision_Approve"));
		//approveButton.click();
		
	}

	@Then("^click \"(.*?)\" on approval/reject pop-up$")
	public void click_on_approval_reject_pop_up(String arg1) throws Throwable {

		String parentWindowHandler = cp.driver.getWindowHandle();
        String subWindowHandler = null;
        Set<String> handles = cp.driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        
        cp.driver.switchTo().window(subWindowHandler);
        
        if(arg1.equalsIgnoreCase("Reject")){
        	WebElement rejectComments=cp.driver.findElement(By.xpath(".//*[@id='InputText8']/textarea"));	
            rejectComments.sendKeys("Rejected");
            WebElement rejectButton=cp.driver.findElement(By.xpath("html/body/div[4]/div[3]/div/button[1]"));	
            rejectButton.click();
        } else if(arg1.equalsIgnoreCase("Approve")){
        	
        	WebElement approveComments=cp.driver.findElement(By.xpath("//*[@id='InputText14']/textarea"));	//*[@id="InputText14"]/textarea
        	approveComments.sendKeys("Approved");
            WebElement approveButton=cp.driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/button[1]"));	
            approveButton.click();
        }
        
        
		cp.driver.switchTo().window(cp.parentWindowHandler);
	}
	
	@When("^fill delete details pop-up and click delete$")
	public void fill_delete_details_pop_up_and_click_delete() throws Throwable {
	    
		String parentWindowHandler = cp.driver.getWindowHandle();
        String subWindowHandler = null;
        Set<String> handles = cp.driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        
        cp.driver.switchTo().window(subWindowHandler);
		WebElement deleteComments=cp.driver.findElement(By.xpath("//*[@id='InputText14']/textarea"));	//*[@id="InputText14"]/textarea
    	deleteComments.sendKeys("Deleted");
        WebElement deleteButton=cp.driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/button[1]"));
        deleteButton.click();
        
        cp.driver.switchTo().window(parentWindowHandler);
        //Thread.sleep(2000);
	}
	
	@When("^click on case creation pop-up$")
	public void click_on_case_creation_pop_up() throws Throwable {

		String parentWindowHandler = cp.driver.getWindowHandle();
		
        String subWindowHandler = null;

        Set<String> handles = cp.driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        //System.out.println(subWindowHandler + parentWindowHandler + cp.secondWindowHandler + cp.parentWindowHandler);
        cp.driver.switchTo().window(subWindowHandler);
        
        WebElement caseRefText=cp.driver.findElement(By.xpath("//*[@id='CustomHTML3']/b/h3/font"));
        cp.caseReference=caseRefText.getText();
        
        System.out.println(cp.caseReference);
		
        WebElement confirmButton;
        try{
        	confirmButton=cp.driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/button"));
        }
        catch(NoSuchElementException ne){
        	confirmButton=cp.driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/button"));
        }
			//html/body/div[3]/div[3]/div/button/span	//span[contains(text(),'Done')]/parent::button		/html/body/div[7]/div[3]/div/button	/html/body/div[7]/div[3]/div/button
		Actions actions = new Actions(cp.driver);
		actions.moveToElement(confirmButton).click().perform();
		//confirmButton.click();
	
		try{
		
			cp.driver.switchTo().window(cp.secondWindowHandler);
			cp.driver.close();
		}
		catch (Exception e) {
			
		}
		
		cp.driver.switchTo().window(cp.parentWindowHandler);
	}

	
	@Then("^store case reference and close the case submission pop-up$")
	public void store_case_reference_and_close_the_case_submission_pop_up() throws Throwable {

		WebElement popUpWindow=cp.driver.findElement(By.cssSelector("div[aria-labelledby='ui-dialog-title-section11']"));
		
		WebElement caseRefText=popUpWindow.findElement(By.xpath("./div[2]/div/table/tbody/tr/td/div/b/h3/font"));	//*[@id="CustomHTML8"]/b/h3/font
        cp.caseReference=caseRefText.getText();
        System.out.println(cp.caseReference);
        
        WebElement confirmButton=popUpWindow.findElement(By.xpath("./div[3]/div/button"));	//html/body/div[8]/div[3]/div/button/span
        confirmButton.click();
        
        try{
    		
			cp.driver.switchTo().window(cp.secondWindowHandler);
			cp.driver.close();
		}
		catch (Exception e) {
			
		}
		
		cp.driver.switchTo().window(cp.parentWindowHandler);
        
	}
	
	
	@Then("^select Type, Entry and Entry Type$")
	public void select_Type_Entry_and_Entry_Type() throws Throwable {
	    
		cp.parentWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
		
		Select Typedropdown = new Select(cp.driver.findElement(By.name("tw#local#listType")));
		Typedropdown.selectByVisibleText("List Entry");
		Select Entrydropdown = new Select(cp.driver.findElement(By.name("tw#local#entryType")));
		Entrydropdown.selectByVisibleText("Private");
		//Select EntryTypedropdown = new Select(cp.driver.findElement(By.xpath("//*[@id='Label3']/select")));
		//EntryTypedropdown.selectByVisibleText("Individual");
		
		Select listNameDropdown = new Select(cp.driver.findElement(By.xpath("//*[@id='InputText4']/select")));
		listNameDropdown.selectByVisibleText(PreviousListName);
		
		
	}
	
	@Then("^select Type as \"([^\"]*)\", Entry as \"([^\"]*)\" and Entry Type as \"([^\"]*)\"$")
	public void select_Type_as_Entry_as_and_Entry_Type_as(String list_type, String entry, String entry_type) throws Throwable {

		cp.parentWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
		
		Select Typedropdown = new Select(cp.driver.findElement(By.name("tw#local#listType")));
		Typedropdown.selectByVisibleText(list_type);
		Select Entrydropdown = new Select(cp.driver.findElement(By.name("tw#local#entryType")));
		Entrydropdown.selectByVisibleText(entry);
		Select EntryTypedropdown = new Select(cp.driver.findElement(By.xpath("//*[@id='Label3']/select")));
		EntryTypedropdown.selectByVisibleText(entry_type);
		
		//Select listNameDropdown = new Select(cp.driver.findElement(By.xpath("//*[@id='InputText4']/select")));
		//listNameDropdown.selectByVisibleText(PreviousListName);
		
	}
	
	@Then("^click on edit on private entry details page$")
	public void click_on_edit_on_private_entry_details_page() throws Throwable {

		WebElement editButton=cp.driver.findElement(By.id("managerDecision_Approve"));
		editButton.click();
	}

	@Then("^edit the fields on Edit Private List Record page$")
	public void edit_the_fields_on_Edit_Private_List_Record_page() throws Throwable {
	    
		//WebElement countryResidence=cp.driver.findElement(By.name("tw#local#privateListRecordInfo#countryOfResidence"));
		//countryResidence.sendKeys("UK");
		WebElement buContact=cp.driver.findElement(By.name("tw#local#privateListRecordInfo#owner"));
		buContact.clear();
		buContact.sendKeys("5465436");
		
	}

	@Then("^fill delete details pop-up and click delete for list entry$")
	public void fill_delete_details_pop_up_and_click_delete_for_list_entry() throws Throwable {

		String parentWindowHandler = cp.driver.getWindowHandle();
        String subWindowHandler = null;
        Set<String> handles = cp.driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        
        cp.driver.switchTo().window(subWindowHandler);
		WebElement deleteComments=cp.driver.findElement(By.xpath("//*[@id='InputText14']/textarea"));	//*[@id="InputText14"]/textarea
    	deleteComments.sendKeys("Deleted");
        WebElement deleteButton=cp.driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/button[1]"));		//html/body/div[5]/div[3]/div/button[1]/span
        deleteButton.click();
        
        cp.driver.switchTo().window(parentWindowHandler);
     
	}
	
	@Then("^fill suppression details pop-up and click suppress$")
	public void fill_suppression_details_pop_up_and_click_suppress() throws Throwable {
		
    	WebElement suppressionWindow=cp.driver.findElement(By.cssSelector("div[aria-labelledby='ui-dialog-title-ApprovalReason1'"));
    	
    	WebElement Screening=suppressionWindow.findElement(By.xpath("./div[2]/div/table/tbody/tr/td/div[3]/div/div/input"));	//*[@id="InputText14"]/textarea
	    Screening.click();
    	
	    WebElement Comments=suppressionWindow.findElement(By.xpath(".//textarea"));	//*[@id="InputText14"]/textarea
    	Comments.sendKeys("Suppressed");
	    
    
    	WebElement actionButton=suppressionWindow.findElement(By.className("ui-dialog-buttonset")).findElement(By.xpath(".//button[1]"));	
    	actionButton.click();
	}
	
	@Then("^fill suppression details pop-up and click suppress for public list entry$")
	public void fill_suppression_details_pop_up_and_click_suppress_for_public_list_entry() throws Throwable {

		WebElement suppressionWindow=cp.driver.findElement(By.cssSelector("div[aria-labelledby='ui-dialog-title-ApprovalReason'"));
    	
    	WebElement Screening=suppressionWindow.findElement(By.xpath("./div[2]/div/table/tbody/tr/td/div[3]/div/div/input"));	//*[@id="InputText14"]/textarea
	    Screening.click();
    	
	    WebElement Comments=suppressionWindow.findElement(By.xpath(".//textarea"));	//*[@id="InputText14"]/textarea
    	Comments.sendKeys("Suppressed");
	    
    
    	WebElement actionButton=suppressionWindow.findElement(By.className("ui-dialog-buttonset")).findElement(By.xpath(".//button[1]"));	
    	actionButton.click();		
	}
	
	@When("^Select Private List Entry from the drop down and Submit$")
	public void select_Private_List_Entry_from_the_drop_down_and_Submit() throws Throwable {
		cp.parentWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
		
		Select dropdown = new Select(cp.driver.findElement(By.xpath(".//*[@id='ComboBox0']/select")));
		dropdown.selectByVisibleText("Private List Entry");
			
		WebElement sudmitButton=cp.driver.findElement(By.id("ButtonGroup0_Button0"));		//*[@id="ButtonGroup0_Button0"]/span
		sudmitButton.click();
		
		Select listNameDropdown=new Select (cp.driver.findElement(By.name("tw#local#privateListRecord#listNameInt")));		//*[@id="ButtonGroup0_Button0"]/span
		//listNameDropdown.selectByIndex(2);
		listNameDropdown.selectByVisibleText(PreviousListName);
		
	}
	
	@When("^Select Group Type$")
	public void select_Group_Type(DataTable arg1) throws Throwable {
		Select groupTypeDropdown=new Select (cp.driver.findElement(By.name("tw#local#privateListRecord#groupTypeInt")));		//*[@id="ButtonGroup0_Button0"]/span
		groupTypeDropdown.selectByIndex(1);
	}

	@When("^Enter mandatory details of Individual and submit$")
	public void enter_mandatory_details_of_Individual_and_submit(DataTable arg1) throws Throwable {
		
		Random rand = new Random();
		int randomNum = rand.nextInt(10001) + 10000;

		Title= arg1.asMaps(String.class, String.class).get(0).get("Title");
		FirstName=arg1.asMaps(String.class, String.class).get(0).get("FirstName");
		FirstName=FirstName + randomNum;
		LastName=arg1.asMaps(String.class, String.class).get(0).get("LastName");
		OrginatingBU=arg1.asMaps(String.class, String.class).get(0).get("OrginatingBU");
		BUContact=arg1.asMaps(String.class, String.class).get(0).get("BUContact");
		StartDate=arg1.asMaps(String.class, String.class).get(0).get("StartDate");
		CustomerStatus=arg1.asMaps(String.class, String.class).get(0).get("CustomerStatus");
		ReviewPeriod=arg1.asMaps(String.class, String.class).get(0).get("ReviewPeriod");
		Jurisdiction=arg1.asMaps(String.class, String.class).get(0).get("Jurisdiction");
		Why=arg1.asMaps(String.class, String.class).get(0).get("Why");
		What=arg1.asMaps(String.class, String.class).get(0).get("What");
		Comments=arg1.asMaps(String.class, String.class).get(0).get("Comments");
		
		System.out.println(FirstName + LastName);
		
		WebElement panel1=cp.driver.findElement(By.xpath(".//*[@id='section5']/h3"));
		Actions actions = new Actions(cp.driver);
		actions.moveToElement(panel1).click().perform();
		
		Thread.sleep(1000);
		WebElement panel2=cp.driver.findElement(By.xpath(".//*[@id='section7']/h3"));
		actions = new Actions(cp.driver);
		actions.moveToElement(panel2).click().perform();
		//JavascriptExecutor jse = (JavascriptExecutor)cp.driver;
		//jse.executeScript("arguments[0].scrollIntoView()", panel2);
		//panel2.click();
		
		Thread.sleep(1000);
		WebElement panel3=cp.driver.findElement(By.xpath(".//*[@id='section4']/h3"));
		actions = new Actions(cp.driver);
		actions.moveToElement(panel3).click().perform();

		WebElement oTitle=cp.driver.findElement(By.name("tw#local#privateListRecord#title"));
		oTitle.sendKeys(Title);
		WebElement oFirstName=cp.driver.findElement(By.name("tw#local#privateListRecord#firstName"));
		oFirstName.sendKeys(FirstName);
		WebElement oLastName=cp.driver.findElement(By.name("tw#local#privateListRecord#lastName"));
		oLastName.sendKeys(LastName);
		System.out.println(OrginatingBU);
		Select oOrginatingBU = new Select(cp.driver.findElement(By.name("tw#local#privateListRecord#businessUnitInt")));
		oOrginatingBU.selectByVisibleText(OrginatingBU);
		
		WebElement oBUContact=cp.driver.findElement(By.name("tw#local#privateListRecord#owner"));
		oBUContact.sendKeys(BUContact);
		Select oCustomerStatus = new Select(cp.driver.findElement(By.name("tw#local#privateListRecord#customerStatusInt")));
		oCustomerStatus.selectByVisibleText(CustomerStatus);
		Select oReviewPeriod = new Select(cp.driver.findElement(By.name("tw#local#privateListRecord#reviewPeriodInt")));
		oReviewPeriod.selectByVisibleText(ReviewPeriod);
		WebElement oStartDate=cp.driver.findElement(By.name("tw#local#privateListRecord#startDate"));
		oStartDate.sendKeys(StartDate);
		//Select oJurisdiction = new Select(cp.driver.findElement(By.name("tw#local#privateListRecord#jurisdictionInt")));
		//oJurisdiction.selectByVisibleText(Jurisdiction);
		Select oWhy = new Select(cp.driver.findElement(By.name("tw#local#privateListRecord#reasonForWhyInt")));
		oWhy.selectByVisibleText(Why);
		Select oWhat = new Select(cp.driver.findElement(By.name("tw#local#privateListRecord#reasonForWhatInt")));
		oWhat.selectByVisibleText(What);
		WebElement oComments=cp.driver.findElement(By.name("tw#local#privateListRecord#comments"));
		oComments.sendKeys(Comments);
		
		WebElement submitButton=cp.driver.findElement(By.id("ButtonGroup3_Button0"));
		submitButton.click();
		
	}

	@When("^validate entered details in confirmation page$")
	public void validate_entered_details_in_confirmation_page() throws Throwable {
		
		
	}
	
	@When("^press submit on Private List Entry submit page$")
	public void press_submit_on_Private_List_Entry_submit_page() throws Throwable {
		WebElement submitButton=cp.driver.findElement(By.id("ButtonGroup0_Button0"));
		submitButton.click();
		
		String parentWindowHandler = cp.driver.getWindowHandle();
		
        String subWindowHandler = null;

        Set<String> handles = cp.driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        
        cp.driver.switchTo().window(subWindowHandler);
        WebElement CaseRefText=cp.driver.findElement(By.xpath(".//*[@id='CustomHTML3']/b/h3/font"));
        cp.caseReference=CaseRefText.getText();
        System.out.println(cp.caseReference);
        WebElement doneButton=cp.driver.findElement(By.xpath("html/body/div[2]/div[3]/div/button"));
        doneButton.click();
		cp.driver.switchTo().window(parentWindowHandler);
		cp.driver.close();
		cp.driver.switchTo().window(cp.parentWindowHandler);
	}

	@Then("^fill add alias details pop-up and click add alias$")
	public void fill_add_alias_details_pop_up_and_click_add_alias() throws Throwable {

		WebElement name=cp.driver.findElement(By.xpath("//*[@id='InputText9']/input"));
        name.sendKeys("aaa");
        
        WebElement comment=cp.driver.findElement(By.xpath("//*[@id='InputText12']/textarea"));
        comment.sendKeys("aaa");
        
        WebElement button=cp.driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/button[1]/span"));
        button.click();
	}

	@Then("^select the alias to suppress$")
	public void select_the_alias_to_suppress() throws Throwable {

		Iterator<WebElement> checkboxes=cp.driver.findElements(By.cssSelector("td[aria-describedby='AliasHistory_checkbox']")).iterator();
        //System.out.println(checkboxes.
		while(checkboxes.hasNext()){
        	
        	WebElement checkbox=checkboxes.next().findElement(By.xpath(".//input[@type='checkbox']"));
        	if(!checkbox.isSelected()){
        		checkbox.click();
        	}       	
        }
	}

	@Then("^I search case reference with \"(.*?)\"$")
	public void i_search_case_reference_with(String arg1) throws Throwable {

		WebDriverWait wait = new WebDriverWait(cp.driver, 10);
		//wait.until(ExpectedConditions.
		
		for(int i=1;i<=3;i++){
			try{
				wait.until( ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='gs_caseRef']")));
		
			}catch (TimeoutException e) {
				WebElement refreshTaskListButton=cp.driver.findElement(By.xpath(".//*[@id='ButtonGroup0_Button0']"));
				refreshTaskListButton.click();
				//System.out.println(e);
			}
		}
		String taskListSearchXpath="//*[@id='gs_caseRef']";
		oWE=cp.driver.findElement(By.xpath(taskListSearchXpath));
		oWE.sendKeys(arg1 + "\n");
		cp.caseReference=arg1;
	}

	@Then("^expand \"([^\"]*)\" details$")
	public void expand_details(String arg1) throws Throwable {

		WebElement groupText=cp.driver.findElement(By.xpath ("//*[@id='section9']/h3"));
		Actions actions = new Actions(cp.driver);
		actions.moveToElement(groupText).click().perform();
		Thread.sleep(1000);
		
	}
	
	@When("^select Type as \"([^\"]*)\", Entry as \"([^\"]*)\"$")
	public void select_Type_as_Entry_as(String list_type, String entry) throws Throwable {

		cp.parentWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
		
		Select Typedropdown = new Select(cp.driver.findElement(By.name("tw#local#listType")));
		Typedropdown.selectByVisibleText(list_type);
		Select Entrydropdown = new Select(cp.driver.findElement(By.xpath("//*[@id='ComboBox7']/select")));
		Entrydropdown.selectByVisibleText(entry);
		
	}
	
	@When("^click on first \"([^\"]*)\" from the list on search list and entry page$")
	public void click_on_first_from_the_list_on_search_list_and_entry_page(String arg1) throws Throwable {

		WebElement linkColumn=cp.driver.findElement(By.cssSelector("td[aria-describedby='searchCaseTable_idString']"));
		WebElement listLink=linkColumn.findElement(By.xpath("./a[1]"));
		listLink.click();
		cp.secondWindowHandler = cp.driver.getWindowHandle();
		for(String winHandle : cp.driver.getWindowHandles()){
		    cp.driver.switchTo().window(winHandle);
		}
	}

	@Then("^New CTRP page opens$")
	public void new_CTRP_page_opens() throws Throwable {

		WebElement titleElement=cp.driver.findElement(By.id("title"));
		String titleText=titleElement.getText();
		org.junit.Assert.assertTrue(titleText.equalsIgnoreCase("CTRP Details"));
		
	}
	
	@When("^waitnow$")
	public void waitnow() throws Throwable {
		Thread.sleep(10000);
	}
	
	
}
