package com.github.coreconcepts;

/**
 * <p>Defining a Method with the Same Signature as a Superclass's Method
 * <P>OverHiding: Static access from reference side.</p>
 * <p>OverRiding: Instance access from constructor side.</p>
 * 
<pre>
O/P «
Static A
Instance B
Static B - Over Hiding
Instance B - Over Riding
</pre>
 * 
 * <UL>
 *   <LI>If Class or Method is final we can't Override it.</LI>
 * </UL>
 * 
 * @author yashwanth.m
 *
 */
class Horse {
    public String identifyMyself() {
        return "I am a horse.";
    }
}
interface Animal {
    default public String identifyMyself() {
        return "I am an animal.";
    }
}
interface Flyer extends Animal {
    default public String identifyMyself() {
        return "I am able to fly.";
    }
}
interface Mythical extends Animal {
    default public String identifyMyself() {
        return "I am a mythical creature.";
    }
}

/**
 * Instance methods are preferred over interface default methods.
 * 
 * @author yashwanth.m
 *
 */
class TestMethods implements Mythical, Flyer {

	@Override
	public String identifyMyself() {
		return Flyer.super.identifyMyself();
	}
	
}
public class Over_Load_Ride {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Method_Declaration b = new Method_Declaration();
		b.one();
		b.two();
		
		// Polymorphism
		Method_Signature a = new Method_Declaration();
		a.one();
		a.two();
		a.three(2);
		a.three( new Integer(10));
		a.three("a");
		
		a.twoException();
		a.two2Exception();
		
		TestMethods obj = new TestMethods();
		String returnObj = obj.identifyMyself();
		System.out.println(" « "+ returnObj);
	}
}
/**
 * <B>Overloading Method Signatures in same class</B>
 * <p>
 * Method signature: It consists of method name and
 * parameter list (number of parameters, type of the parameters and order of the parameters).
 * 
 * <pre>
 * {@code
 *   methodName(parametersList y) 
 * }
 * </pre>
 * </p>
 * @author yashwanth.m
 *
 */
class Method_Signature {
	public static void one() {
		System.out.println("Static A");
	}
	public void two() {
		System.out.println("Instance A");
	}
	public void twoException() throws RuntimeException {
		System.out.println("Instance A - twoException");
	}
	void two2Exception() {
		System.out.println("Instance A - twoException");
	}
	
	// Overloaded Methods with Same Signature
	public void three(int a){
		System.out.println("Parameter and Return Type");
	}
	public Integer three(Integer a){
		System.out.println("Integer : Paramerter Change");
		return a;
	}
	/*public void three(Integer a){
		System.out.println("Integer : Paramerter Change : Leads to error");
	}*/
	public String three(String a){
		System.out.println("String : Paramerter Change");
		return "";
	}
	String three(Object a){
		System.out.println("Object : Paramerter Change");
		return "";
	}
}
/**
 * <B>OverRidding Method Declarations form its parent class</B>
 * <p>
 * Method signature: It consists of method name and
 * parameter list (number of parameters, type of the parameters and order of the parameters).
 * <br />
 * accessModifier « Cannot reduce the visibility of the inherited method from parent class
 * <pre>
 * {@code
 *   accessModifier returnType methodName(paramtersList ...) throwsExceptionsList {
 *       methodBody
 *   }
 *   
 *   public void m1(int a) throws Exception {
 *       ...
 *   }
 * }
 * </pre>
 * NOTE: Parent_Class method throws
 * Checked exceptions are checked at compile-time.(try-catch | throws)
 * Unchecked exceptions are occurred at Runtime due to the bad data provided|Interactions
 * </p>
 * @author yashwanth.m
 *
 */
class Method_Declaration extends Method_Signature {
	public static void one() {
		System.out.println("Static B - Over Hiding");
	}
	@Override
	public void two(){
		System.out.println("Instance B - Over Riding");
	}
	@Override
	public void twoException() {
		System.out.println("Instance B -  Over Riding - twoException");
	}
	@Override
	public void two2Exception() { //  throws Exception « exception is not compilable.
		try {
			System.out.println("Instance B - two2Exception");
		} catch (Exception e) {
		}
	}
}