package singleton_holders;

import java.util.ArrayList;

import cell_data_holders.Cell;
import cycle_components.StaticCalculations;
import environment_data_holders.GCell;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;

public class SingletonStatStore {
	
	//stat data
		//deaths
	private static int totalDeaths = 0;
		//births
	private static int totalBirths = 0;
	
	
	private static int totalCells = 0;
	
	private static boolean gather = true;
	
	//private static int upperBin = 0;
	//private static int lowerBin = 0;
	//private static int cut = 0;
	private static int count = 0;
	
	
	public static void clean() {
		System.out.println("cleaning");
		
			//deaths
		totalDeaths = 0;
			//births
		totalBirths = 0;
		
		
		totalCells = 0;
		/*upperBin = 0;
		lowerBin = 0;
		cut = 0;*/
		
		
	}
	
	public static int getTotalCells() {
		return totalCells;
	}

	public static void setTotalCells(int totalCells) {
		SingletonStatStore.totalCells = totalCells;
	}
	
	public static void incTotalCells()
	{
		totalCells++;
	}
	
	

	public static int getTotalDeaths() {
		return totalDeaths;
	}

	public static void setTotalDeaths(int totalDeaths) {
		SingletonStatStore.totalDeaths = totalDeaths;
	}

	public static int getTotalBirths() {
		return totalBirths;
	}
	
	public static void setTotalBirths(int totalBirths) {
		SingletonStatStore.totalBirths = totalBirths;
	}

	public static void incrementDeaths() {
		totalDeaths++;
		
	}

	public static void incrementBirths() {
		totalBirths++;
		
	}

	
	

	public static void decrementCells() {
		totalCells--;
		
	}

	public static void pollBounceTurn(Cell cell) {
		double o = cell.getVect();
		if(o <=45)
		{
			cell.getPack().addBounceSectOne(1.00);
		}else
		{
			if(o<=90)
			{
				cell.getPack().addBounceSectTwo(1.00);
			}else
			{
				if(o<=135)
				{
					cell.getPack().addBounceSectThree(1.00);
				}else
				{
					if(o<=180)
					{
						cell.getPack().addBounceSectFour(1.00);
					}else
					{
						if(o<=225)
						{
							cell.getPack().addBounceSectFive(1.00);
						}else
						{
							if(o<=270)
							{
								cell.getPack().addBounceSectSix(1.00);
							}else
							{
								if(o<=315)
								{
									cell.getPack().addBounceSectSeven(1.00);
								}else
								{
									cell.getPack().addBounceSectEight(1.00);
								}
							}
						}
					}
				}
			}
		}
		
		
	}
	
	public static void pollTurnType(Cell cell, Posi posi) {
		boolean moveLifeGatePass = false;
		if(isGather() )
		{
			if(cell.getPack().getLifetime() > 1 &&cell.getSpeed() >0|| moveLifeGatePass)
			{
			GCell g = posi.getGCell(cell.getPositionX(), cell.getPositionY());
			int[] a = g.getRelativeAngle();
		
			//System.out.println("gathering "+SingletonHolder.getIncrement());
			boolean norm = false;
			if(norm)
			{
				
				double o = cell.getVect();

				if(cell.getIncSpeed() < 0)
				{
					o = StaticCalculations.getOppositeAngle(0);
				}
				double l = cell.getPrevVect();
				//double m =(o-l);
				/*double m = 0.00;
				if(o >l)
				{
					m = o-l;
				}else
				{
					m = l-o;
				}
				//double phi = Math.abs(o - l) ; 
				double phi = Math.abs(o - l) % 360;       // This is either the distance or 360 - distance
			    double distance = phi > 180 ? 360 - phi : phi;
				double m = distance;*/
				double m = o-l;
				if(m < 0)
				{
					m = 360.00+m;
				}
				if(m < 0)
				{
					//System.out.println("!");
				}
				boolean microClean = true;
				//m > 0.01 && m < 359.99
				if(m >= 0.01 && m <= 359.99 || microClean == true)
				{
					//if(m == 0)
					//{
						
					//}else
					//{
					//System.out.println(""+o+" "+l);
					if( m <=22.5)
					{
						cell.getPack().addChosenSectOne(1.00);
						a[0]=a[0]+1;
					}else
					{
						if(m<=45.00)
						{
							cell.getPack().addChosenSectTwo(1.00);
							a[1]=a[1]+1;
						}else
						{
							if(m<=67.5)
							{
								cell.getPack().addChosenSectThree(1.00);
								a[2]=a[2]+1;
							}else
							{
								if(m<=90.00)
								{
									cell.getPack().addChosenSectFour(1.00);
									a[3]=a[3]+1;
								}else
								{
									if(m<=112.5)
									{
										cell.getPack().addChosenSectFive(1.00);
										a[4]=a[4]+1;
									}else
									{
										if(m<=135.00)
										{
											cell.getPack().addChosenSectSix(1.00);
											a[5]=a[5]+1;
										}else
										{
											if(m<=157.5)
											{
												cell.getPack().addChosenSectSeven(1.00);
												a[6]=a[6]+1;
											}else
											{
												if(m<=180.00)
												{
													cell.getPack().addChosenSectEight(1.00);
													a[7]=a[7]+1;
												}else
												{
													if(m<=202.5)
													{
														cell.getPack().addChosenSectNine(1.00);
														a[8]=a[8]+1;
													}else
													{
														if(m<=225.00)
														{
															cell.getPack().addChosenSectTen(1.00);
															a[9]=a[9]+1;
														}else
														{
															if(m<=247.5)
															{
																cell.getPack().addChosenSectEleven(1.00);
																a[10]=a[10]+1;
															}else
															{
																if(m<=270.00)
																{
																	cell.getPack().addChosenSectTwelve(1.00);
																	a[11]=a[11]+1;
																}else
																{
																	if(m<=292.5)
																	{
																		cell.getPack().addChosenSectThirteen(1.00);
																		a[12]=a[12]+1;
																	}else
																	{
																		if(m<=315.00)
																		{
																			cell.getPack().addChosenSectFourteen(1.00);
																			a[13]=a[13]+1;
																		}else
																		{
																			if(m<=337.5)
																			{
																				cell.getPack().addChosenSectFifteen(1.00);
																				a[14]=a[14]+1;
																			}else
																			{
																				if(m<=360.00)
																				{
																					cell.getPack().addChosenSectSixteen(1.00);
																					a[15]=a[15]+1;
																				}else
																				{
																					System.out.println("summits fucky poll "+o+" "+l);
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
											}
										
										}
									}
								}
							}
						}
					}
				//}
													
				SingletonStatStore.pollAbs(cell.getVect(),cell,posi);
				}else
				{
					/*if(o < l)
					{
						o += 360;
					}
					double m = o-l;
					if(m < 0)
					{
						m = Math.pow(m, 2);
						m = Math.sqrt(m);
					}
					double phi = Math.abs(o - l) % 360;       // This is either the distance or 360 - distance
				    double distance = phi > 180 ? 360 - phi : phi;
				    double m = distance;*/
					double o = cell.getVect();
					if(cell.getIncSpeed() < 0)
					{
						o = StaticCalculations.getOppositeAngle(0);
					}
					double l = cell.getPrevVect();
					double m = o-l;
					if(m < 0)
					{
						m = 360.00+m;
					}
					if(m < 0)
					{
						//System.out.println("!");
					}
					boolean overlapClean = false;
					if(cell.getOverlappers() == 0|| overlapClean == false)
					{
						boolean microClean = false;
						//m > 0.01 && m < 359.99
						//m > 0.7 && m < 359.3
						if(m > 0.7 && m < 359.3 || microClean == false)
						{
						Boolean igZero = false;
							if(m == 0 && igZero)
							{
								count++;
								System.out.println("0 at "+o+" "+l+" time "+SingletonHolder.getIncrement()+" jumps since birth "+cell.getPack().getLifetime()+ " cell "+cell.getUnique()+" count "+count+" distance "+cell.getSpeed());
							}else
							{
							//System.out.println(""+o+" "+l);
							
								if(m<=22.5|| m > 337.5)
								{
									cell.getPack().addChosenSectOne(1.00);
									a[0]=a[0]+1;
								}else
								{
									
										if(m<=67.5)
										{
											cell.getPack().addChosenSectTwo(1.00);
											a[1]=a[1]+1;
										}else
										{
											
												if(m<=112.5)
												{
													cell.getPack().addChosenSectThree(1.00);
													a[2]=a[2]+1;
												}else
												{
													
														if(m<=157.5)
														{
															cell.getPack().addChosenSectFour(1.00);
															a[3]=a[3]+1;
														}else
														{
															
																if(m<=202.5)
																{
																	cell.getPack().addChosenSectFive(1.00);
																	a[4]=a[4]+1;
																}else
																{
																	
																		if(m<=247.5)
																		{
																			cell.getPack().addChosenSectSix(1.00);
																			a[5]=a[5]+1;
																		}else
																		{
																			
																				if(m<=292.5)
																				{
																					cell.getPack().addChosenSectSeven(1.00);
																					a[6]=a[6]+1;
																				}else
																				{
																					
																						if(m<=337.5)
																						{
																							cell.getPack().addChosenSectEight(1.00);
																							a[7]=a[7]+1;
																						}else
																						{
																							System.out.println("summits fucky poll "+o+" "+l);
																						}
																					}
																				}
																}
														}
												}
										}
								}
							}
							double oX = 0.00;
							double oY = 0.00;
							if(cell.getTail().size() >2)
							{
								oX = cell.getTail().get(cell.getTail().size()-2)[0];
								oY = cell.getTail().get(cell.getTail().size()-2)[1];
							}
							//System.out.println("norm "+m+" x "+cell.getPositionX()+" y "+cell.getPositionY()+" oX "+oX+" oY "+oY+" lower tot "+lowerBin+" upper tot"+upperBin+" cell "+cell.getUnique()+" time "+SingletonHolder.getIncrement()+" cut "+cut);
							
								//lowerBin++;
						}
						else
						{
							double oX = 0.00;
							double oY = 0.00;
							if(cell.getTail().size() >2)
							{
								oX = cell.getTail().get(cell.getTail().size()-2)[0];
								oY = cell.getTail().get(cell.getTail().size()-2)[1];
							}
							/*System.out.println("micro clean "+m+" x "+cell.getPositionX()+" y "+cell.getPositionY()+" oX "+oX+" oY "+oY+" lower tot "+lowerBin+" upper tot"+upperBin+" cell "+cell.getUnique()+" time "+SingletonHolder.getIncrement()+" cut "+cut);
							if(m<0.7)
							{
								lowerBin++;
							}else
							{
								upperBin++;
							}*/
						}
					}
					else 
					{
						//cut++;
						//System.out.println("cut "+cut);
					}
																		
																	
																
															
														
													
												
												
					
														
					SingletonStatStore.pollAbs(cell.getVect(),cell,posi);
				}
			}
		}
	}
	
	

	private static void pollAbs(double m, Cell cell, Posi posi) {
		m = m+90;
		if(m >360)
		{
			m = m-360;
		}
		if(m < 0)
		{
			m = 360.00+m;
		}
		int[] a = posi.getGCell(cell.getPositionX(), cell.getPositionY()).getDirections();
		if(m<=22.5|| m > 337.5)
		{
			cell.getPack().addAbsoluteDirectionOne(1.00);	
			a[0] = a[0]+1;
		}else
		{
			if(m <= 67.5)
			{
				cell.getPack().addAbsoluteDirectionTwo(1.00);
				a[1] = a[1]+1;
			}else
			{
				if(m <= 112.5)
				{
					cell.getPack().addAbsoluteDirectionThree(1.00);
					a[2] = a[2]+1;
				}else
				{
					if(m <= 157.5)
					{
						cell.getPack().addAbsoluteDirectionFour(1.00);
						a[3] = a[3]+1;
					}else
					{
						if(m <= 202.5)
						{
							cell.getPack().addAbsoluteDirectionFive(1.00);
							a[4] = a[4]+1;
						}else
						{
							if(m <= 247.5)
							{
								cell.getPack().addAbsoluteDirectionSix(1.00);
								a[5] = a[5]+1;
							}else
							{
								if(m <= 292.5)
								{
									cell.getPack().addAbsoluteDirectionSeven(1.00);
									a[6] = a[6]+1;
								}else
								{
									if(m <= 337.5)
									{
										cell.getPack().addAbsoluteDirectionEight(1.00);
										a[7] = a[7]+1;
									}
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	public static void pollCollisionTurn(Cell cell) {
		double o = cell.getVect();
		if(o <=45)
		{
			cell.getPack().addCollisionSectorOne(1.00);
		}else
		{
			if(o<=90)
			{
				cell.getPack().addCollisionSectorTwo(1.00);
			}else
			{
				if(o<=135)
				{
					cell.getPack().addCollisionSectorThree(1.00);
				}else
				{
					if(o<=180)
					{
						cell.getPack().addCollisionSectorFour(1.00);
					}else
					{
						if(o<=225)
						{
							cell.getPack().addCollisionSectorFive(1.00);
						}else
						{
							if(o<=270)
							{
								cell.getPack().addCollisionSectorSix(1.00);
							}else
							{
								if(o<=315)
								{
									cell.getPack().addCollisionSectorSeven(1.00);
								}else
								{
									cell.getPack().addCollisionSectorEight(1.00);
								}
							}
						}
					}
				}
			}
		}
		
	}

	public static boolean isGather() {
		return gather;
	}

	public static void setGather(boolean gather) {
		SingletonStatStore.gather = gather;
	}

	

	

}
