# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Areamanager(models.Model):
    idareamanager = models.AutoField(db_column='idAreaManager', primary_key=True)  # Field name made lowercase.
    firstname = models.CharField(db_column='FirstName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    lastname = models.CharField(db_column='LastName', max_length=45, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'areamanager'


class Builder(models.Model):
    idbuilder = models.AutoField(db_column='idBuilder', primary_key=True)  # Field name made lowercase.
    firstname = models.CharField(db_column='FirstName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    lastname = models.CharField(db_column='LastName', max_length=45, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'builder'


class Expensecatergory(models.Model):
    idexpensecatergory = models.AutoField(db_column='idexpenseCatergory', primary_key=True)  # Field name made lowercase.
    catergoryname = models.CharField(unique=True, max_length=45, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'expensecatergory'


class Expenses(models.Model):
    idexpenses = models.AutoField(primary_key=True)
    expensename = models.CharField(db_column='expenseName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    amount = models.DecimalField(max_digits=10, decimal_places=2, blank=True, null=True)
    quantity = models.IntegerField(blank=True, null=True)
    totalcost = models.DecimalField(db_column='totalCost', max_digits=10, decimal_places=2, blank=True, null=True)  # Field name made lowercase.
    catergory = models.ForeignKey(Expensecatergory, models.DO_NOTHING, db_column='catergory', blank=True, null=True)
    propertyid = models.ForeignKey('Property', models.DO_NOTHING, db_column='propertyID', blank=True, null=True)  # Field name made lowercase.
    datepaid = models.DateField(db_column='datePaid', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'expenses'


class Intiatedandcompletejobs(models.Model):
    issueid = models.ForeignKey('Issues', models.DO_NOTHING, db_column='issueID', primary_key=True)  # Field name made lowercase.
    builderassigned = models.ForeignKey(Builder, models.DO_NOTHING, db_column='BuilderAssigned')  # Field name made lowercase.
    jobstarted = models.DateField(db_column='JobStarted', blank=True, null=True)  # Field name made lowercase.
    jobcompleted = models.DateField(db_column='JobCompleted', blank=True, null=True)  # Field name made lowercase.
    comments = models.CharField(db_column='Comments', max_length=45, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'intiatedAndCompleteJobs'
        unique_together = (('issueid', 'builderassigned'),)


class Issues(models.Model):
    idissues = models.AutoField(db_column='idIssues', primary_key=True)  # Field name made lowercase.
    description = models.CharField(db_column='Description', max_length=500, blank=True, null=True)  # Field name made lowercase.
    reportedby = models.CharField(db_column='reportedBy', max_length=50, blank=True, null=True)  # Field name made lowercase.
    date = models.DateField(db_column='Date', blank=True, null=True)  # Field name made lowercase.
    status = models.CharField(db_column='Status', max_length=45, blank=True, null=True)  # Field name made lowercase.
    levelofurgency = models.CharField(db_column='levelOfUrgency', max_length=45, blank=True, null=True)  # Field name made lowercase.
    propertyid = models.ForeignKey('Property', models.DO_NOTHING, db_column='propertyID')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'issues'


class Landlord(models.Model):
    landlordid = models.AutoField(db_column='LandlordID', primary_key=True)  # Field name made lowercase.
    landlordname = models.CharField(db_column='landlordName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    bankaccountno = models.IntegerField(db_column='bankAccountNo', blank=True, null=True)  # Field name made lowercase.
    sortcode = models.CharField(db_column='sortCode', max_length=45, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'landlord'


class Oldtennants(models.Model):
    idoldtennants = models.AutoField(db_column='idOldTennants', primary_key=True)  # Field name made lowercase.
    firstname = models.CharField(db_column='FirstName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    lastname = models.CharField(db_column='LastName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    dob = models.CharField(db_column='DOB', max_length=45, blank=True, null=True)  # Field name made lowercase.
    livesat = models.ForeignKey('Property', models.DO_NOTHING, db_column='LivesAt', blank=True, null=True)  # Field name made lowercase.
    livingfrom = models.DateField(db_column='LivingFrom', blank=True, null=True)  # Field name made lowercase.
    lefton = models.DateField(db_column='LeftOn', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'oldtennants'


class Paidrent(models.Model):
    idrent = models.AutoField(db_column='IDRent', primary_key=True)  # Field name made lowercase.
    propertyid = models.ForeignKey('Property', models.DO_NOTHING, db_column='PropertyID')  # Field name made lowercase.
    datepaid = models.DateField(db_column='DatePaid', blank=True, null=True)  # Field name made lowercase.
    amountpaid = models.DecimalField(db_column='AmountPaid', max_digits=10, decimal_places=2, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'paidrent'


class Property(models.Model):
    propertyid = models.AutoField(db_column='PropertyID', primary_key=True)  # Field name made lowercase.
    flatno = models.CharField(db_column='FlatNo', max_length=45, blank=True, null=True)  # Field name made lowercase.
    doorno = models.CharField(db_column='DoorNo', max_length=45, blank=True, null=True)  # Field name made lowercase.
    firstline = models.CharField(db_column='FirstLine', max_length=45, blank=True, null=True)  # Field name made lowercase.
    postcode = models.CharField(db_column='PostCode', max_length=45, blank=True, null=True)  # Field name made lowercase.
    town = models.CharField(db_column='Town', max_length=45, blank=True, null=True)  # Field name made lowercase.
    borough = models.CharField(db_column='Borough', max_length=45, blank=True, null=True)  # Field name made lowercase.
    areamanager = models.ForeignKey(Areamanager, models.DO_NOTHING, db_column='AreaManager', blank=True, null=True)  # Field name made lowercase.
    contractended = models.DateField(db_column='ContractEnded', blank=True, null=True)  # Field name made lowercase.
    contractstarted = models.DateField(db_column='ContractStarted', blank=True, null=True)  # Field name made lowercase.
    cost = models.DecimalField(db_column='Cost', max_digits=10, decimal_places=2, blank=True, null=True)  # Field name made lowercase.
    dayofmonth = models.IntegerField(db_column='DayOfMonth', blank=True, null=True)  # Field name made lowercase.
    landlord = models.ForeignKey(Landlord, models.DO_NOTHING, db_column='Landlord', blank=True, null=True)  # Field name made lowercase.
    rate = models.DecimalField(db_column='Rate', max_digits=10, decimal_places=2, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'property'


class Tennant(models.Model):
    tennantid = models.AutoField(db_column='tennantID', primary_key=True)  # Field name made lowercase.
    firstname = models.CharField(db_column='FirstName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    lastname = models.CharField(db_column='LastName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    dateofbirth = models.DateField(db_column='DateOfBirth', blank=True, null=True)  # Field name made lowercase.
    livesat = models.ForeignKey(Property, models.DO_NOTHING, db_column='LivesAt', blank=True, null=True)  # Field name made lowercase.
    livingfrom = models.DateField(db_column='LivingFrom', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'tennant'


class Users(models.Model):
    idusers = models.AutoField(db_column='idUsers', primary_key=True)  # Field name made lowercase.
    username = models.CharField(db_column='UserName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    password = models.CharField(db_column='Password', max_length=45, blank=True, null=True)  # Field name made lowercase.
    usertype = models.CharField(db_column='userType', max_length=45, blank=True, null=True)  # Field name made lowercase.
    tennantid = models.ForeignKey(Tennant, models.DO_NOTHING, db_column='tennantID', unique=True, blank=True, null=True)  # Field name made lowercase.
    builderid = models.ForeignKey(Builder, models.DO_NOTHING, db_column='builderID', unique=True, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'users'


class Utilities(models.Model):
    utilid = models.AutoField(db_column='utilID', primary_key=True)  # Field name made lowercase.
    propertyid = models.ForeignKey(Property, models.DO_NOTHING, db_column='PropertyID', blank=True, null=True)  # Field name made lowercase.
    ctaxborough = models.CharField(db_column='CTaxBorough', max_length=45, blank=True, null=True)  # Field name made lowercase.
    ctaxaccntno = models.CharField(db_column='CTaxAccntNo', max_length=45, blank=True, null=True)  # Field name made lowercase.
    ctaxcost = models.DecimalField(db_column='CTaxCost', max_digits=10, decimal_places=2, blank=True, null=True)  # Field name made lowercase.
    enegcomp = models.CharField(db_column='EnegComp', max_length=45, blank=True, null=True)  # Field name made lowercase.
    enegacntno = models.CharField(db_column='EnegAcntNo', max_length=45, blank=True, null=True)  # Field name made lowercase.
    enegcost = models.DecimalField(db_column='EnegCost', max_digits=10, decimal_places=2, blank=True, null=True)  # Field name made lowercase.
    gascomp = models.CharField(db_column='GasComp', max_length=45, blank=True, null=True)  # Field name made lowercase.
    gasacntno = models.CharField(db_column='GasAcntNo', max_length=45, blank=True, null=True)  # Field name made lowercase.
    gascost = models.DecimalField(db_column='GasCost', max_digits=10, decimal_places=2, blank=True, null=True)  # Field name made lowercase.
    watercomp = models.CharField(db_column='WaterComp', max_length=45, blank=True, null=True)  # Field name made lowercase.
    wateracntno = models.CharField(db_column='WaterAcntNo', max_length=45, blank=True, null=True)  # Field name made lowercase.
    watercost = models.DecimalField(db_column='WaterCost', max_digits=10, decimal_places=2, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'utilities'
