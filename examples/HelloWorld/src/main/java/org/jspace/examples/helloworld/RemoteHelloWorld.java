/*******************************************************************************
 * Copyright (c) 2017 Michele Loreti and the jSpace Developers (see the included 
 * authors file).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package org.jspace.examples.helloworld;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;
import org.jspace.SequentialSpace;
import org.jspace.Space;
import org.jspace.SpaceRepository;

/**
 * A simple HelloWorld program.
 * 
 * 
 * @author Michele Loreti
 *
 */
public class RemoteHelloWorld {

	public final static String GATE_URI = "tcp://127.0.0.1:9001/?keep";
	public final static String REMOTE_URI = "tcp://127.0.0.1:9001/aspace?keep";	
	
	public static void main(String[] argv) throws InterruptedException {
		SpaceRepository repository = new SpaceRepository();
		repository.addGate(GATE_URI);
		repository.add("aspace", new SequentialSpace());
		
		Thread t1 = new Thread( () -> {//PONG TREAD: get pong from "PONG" and write ping to "PING" 
			try {
				RemoteSpace space = new RemoteSpace(REMOTE_URI);
				space.put("GREETING","Hello");
				System.out.println("T1 Done!");
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("T1 Error!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("T1 Error!");
			}			
		});		

		Thread t2 = new Thread( () -> {//PONG TREAD: get pong from "PONG" and write ping to "PING" 
			try {
				RemoteSpace space = new RemoteSpace(REMOTE_URI);
				space.put("NAME","World");
				System.out.println("T2 Done!");
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("T2 Error!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("T2 Error!");
			}			
		});		

		Thread t3 = new Thread( () -> {//PONG TREAD: get pong from "PONG" and write ping to "PING" 
			try {				
				RemoteSpace space = new RemoteSpace(REMOTE_URI);
				Object[] greetingData = space.get(new ActualField("GREETING"), new FormalField(String.class));				
				Object[] nameData = space.get(new ActualField("NAME"), new FormalField(String.class));				
				System.out.println(greetingData[1]+" "+nameData[1]+"!");
				System.out.println("T3 Done!");
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("T3 Error!");
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("T2 Error!");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("T2 Error!");
			}			
		});		
		
		t1.start();
		t2.start();
		t3.start();
	}
	
}
