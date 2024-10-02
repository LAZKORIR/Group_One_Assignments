package prob2A;


public class Student {
	private GradeReport report;
	private String name;
	
	public Student (String name, String grade) {
		this.name = name;
		this.report = new GradeReport(grade, this);
	}
	
	
	public String getName() {
		return name;
		
	}
	
	public GradeReport getReport() {
		return report;
	}

}
