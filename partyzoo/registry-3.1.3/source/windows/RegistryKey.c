/*
** Java native interface to the Windows Registry API.
** 
** Authored by Timothy Gerard Endres
** <mailto:time@gjt.org>  <http://www.trustice.com>
** 
** This work has been placed into the public domain.
** You may use this work in any way and for any purpose you wish.
**
** THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
** NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
** OF THIS SOFTWARE, ASSUMES _NO_ RESPONSIBILITY FOR ANY
** CONSEQUENCE RESULTING FROM THE USE, MODIFICATION, OR
** REDISTRIBUTION OF THIS SOFTWARE. 
** 
** Release 3.1.3
**
*/

#include "RegistryKey.h"

#include <wtypes.h>
#include <winbase.h>
#include <malloc.h>
#include <stdio.h>
#include <string.h>

#include "RegistryKeyProto.h"


JNIEXPORT jobject JNICALL
Java_com_ice_jni_registry_RegistryKey_connectRegistry
		( JNIEnv *env, jobject rkObject, jstring jHostName )
	{
	LONG		regErr;
	HKEY		hKey;
	HKEY		remoteKey;
	jstring		jKeyName;
	char		*hostName;
	jobject		result = NULL;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return NULL;

	jKeyName = getRegistryKeyName( env, rkObject );
	if ( isThrowing( env ) )
		return NULL;

	hostName = jStringToNewAscii( env, jHostName );
	if ( hostName == NULL )
		{
		throwOutOfMemoryError( env, "jStringToNewAscii(hostName)" );
		return NULL;
		}

	regErr =
		RegConnectRegistry
			( hostName, hKey, &remoteKey );

	if ( regErr != ERROR_SUCCESS )
		{
		result = NULL;
		throwSpecificRegError
			( env, regErr, "RegConnectRegistry()", NULL, NULL );
		}
	else
		{
		result = newRegistryKey
			( env, remoteKey, jKeyName, FALSE );
		}

	free( hostName );

	return result;
	}

JNIEXPORT jobject JNICALL
Java_com_ice_jni_registry_RegistryKey_openSubKey
		( JNIEnv *env, jobject rkObject,
			jstring subKeyString, jint jAccess )
	{
	LONG		regErr;
	HKEY		hKey;
	HKEY		hSubKey;
	DWORD		access = (DWORD)jAccess;
	jstring		keyName;
	char		*subKeyName;
	jobject		result = NULL;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return NULL;

	subKeyName = jStringToNewAscii( env, subKeyString );
	if ( subKeyName == NULL )
		{
		throwOutOfMemoryError( env, "jStringToNewAscii" );
		return NULL;
		}

	access =
		((jAccess == 0) ? KEY_READ :
		((jAccess == 1) ? KEY_READ :
		((jAccess == 2) ? KEY_WRITE :
		((jAccess == 3) ? KEY_EXECUTE :
		((jAccess == 4) ? KEY_ALL_ACCESS :
			KEY_READ
		)))));

	regErr = RegOpenKeyEx( hKey, subKeyName, 0, access, &hSubKey);

	if ( regErr != ERROR_SUCCESS )
		{
		result = NULL;
		throwSpecificRegError
			( env, regErr, "RegOpenKeyEx()", subKeyName, NULL );
		}
	else
		{
		keyName = buildFullKeyName( env, rkObject, subKeyName );
		if ( isThrowing( env ) )
			result = NULL;
		else
			{
			result = newRegistryKey
				( env, hSubKey, keyName, FALSE );
			}
		}

	free( subKeyName );

	return result;
	}

JNIEXPORT jobject JNICALL
Java_com_ice_jni_registry_RegistryKey_createSubKey
		( JNIEnv *env, jobject rkObject, jstring subKeyString,
			jstring jClassName, jint jAccess )
	{
	LONG		regErr;
	HKEY		hKey;
	HKEY		hSubKey;
	DWORD		disposition;
	DWORD		access = (DWORD)jAccess;
	jstring		keyName;
	char		*className;
	char		*subKeyName;
	jobject		result = NULL;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return NULL;

	subKeyName = jStringToNewAscii( env, subKeyString );
	if ( subKeyName == NULL )
		{
		throwOutOfMemoryError( env, "jStringToNewAscii(subKeyName)" );
		return NULL;
		}

	className = NULL;
	if ( jClassName != NULL )
		className = jStringToNewAscii( env, jClassName );

	if ( className == NULL )
		{
		throwRegErrorException
			( env, ERROR_INVALID_PARAMETER, "null classname" );
		return NULL;
		}

	access =
		((jAccess == 0) ? KEY_READ :
		((jAccess == 1) ? KEY_READ :
		((jAccess == 2) ? KEY_WRITE :
		((jAccess == 3) ? KEY_EXECUTE :
		((jAccess == 4) ? KEY_ALL_ACCESS :
			KEY_READ
		)))));

	regErr =
		RegCreateKeyEx
			( hKey, subKeyName, 0, className,
				REG_OPTION_NON_VOLATILE, access,
				NULL, &hSubKey, &disposition );

	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegCreateKeyEx()" );
		result = NULL;
		}
	else
		{
		keyName = buildFullKeyName( env, rkObject, subKeyName );
		if ( isThrowing( env ) )
			result = NULL;
		else
			{
			result =
				newRegistryKey
					( env, hSubKey, keyName,
						(disposition == REG_CREATED_NEW_KEY) );
			}
		}

	free( subKeyName );

	return result;
	}

JNIEXPORT void JNICALL
Java_com_ice_jni_registry_RegistryKey_closeKey
		( JNIEnv *env, jobject rkObject )
	{
	HKEY		hKey;
	LONG		regErr;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return;

	regErr = RegCloseKey( hKey );
	}

JNIEXPORT void JNICALL
Java_com_ice_jni_registry_RegistryKey_deleteValue
		( JNIEnv *env, jobject rkObject, jstring valueName )
	{
	HKEY		hKey;
	LONG		regErr;
	char		*lpValueName;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return;

	lpValueName = jStringToNewAscii( env, valueName );
	if ( lpValueName == NULL )
		{
		throwOutOfMemoryError( env, "jStringToNewAscii" );
		return;
		}

	regErr = RegDeleteValue( hKey, lpValueName );
	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegDeleteValue()", NULL, lpValueName );
		}

	free( lpValueName );
	}

JNIEXPORT void JNICALL
Java_com_ice_jni_registry_RegistryKey_deleteSubKey
		( JNIEnv *env, jobject rkObject, jstring subKey )
	{
	HKEY		hKey;
	LONG		regErr;
	char		*lpSubKey;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return;

	lpSubKey = jStringToNewAscii( env, subKey );
	if ( lpSubKey == NULL )
		{
		throwOutOfMemoryError( env, "jStringToNewAscii" );
		return;
		}

	regErr = RegDeleteKey( hKey, lpSubKey );
	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegDeleteKey()", lpSubKey, NULL );
		}

	free( lpSubKey );
	}

JNIEXPORT void JNICALL
Java_com_ice_jni_registry_RegistryKey_flushKey
		( JNIEnv *env, jobject rkObject )
	{
	HKEY		hKey;
	LONG		regErr;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return;

	regErr = RegFlushKey( hKey );
	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegDeleteKey()" );
		}
	}

JNIEXPORT jstring JNICALL
Java_com_ice_jni_registry_RegistryKey_getStringValue
		( JNIEnv *env, jobject rkObject, jstring valueName )
	{
	HKEY		hKey;
	LONG		regErr;
	LPBYTE		szBuf;
	LPTSTR		asciiName;
	jstring		result = NULL;
	LONG		lBufSize = 0;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return NULL;

	asciiName = jStringToNewAscii( env, valueName );
	if ( asciiName == NULL )
		{
		throwOutOfMemoryError( env, "getting ascii valueName from Java string" );
		return NULL;
		}

	regErr =
		RegQueryValueEx
			( hKey, asciiName, NULL, NULL, NULL, &lBufSize );

	if ( regErr != ERROR_SUCCESS && regErr != ERROR_MORE_DATA )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, asciiName );
		free( asciiName );
		return NULL;
		}

	szBuf = malloc( lBufSize + 8 );
	if ( szBuf == NULL )
		{
		throwOutOfMemoryError( env, "allocating string buffer" );
		free( asciiName );
		return NULL;
		}

	regErr =
		RegQueryValueEx
			( hKey, asciiName, NULL, NULL, szBuf, &lBufSize );

	if ( regErr != ERROR_SUCCESS )
		{
		result = NULL;
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, asciiName );
		}
	else
		{
		result = asciiToJString( env, szBuf );
		}

	free( szBuf );
	free( asciiName );

	return result;
	}

JNIEXPORT jstring JNICALL
Java_com_ice_jni_registry_RegistryKey_getDefaultValue
		( JNIEnv *env, jobject rkObject )
	{
	HKEY		hKey;
	LONG		regErr;
	LONG		lBufSize;
	LPBYTE		lpBuffer;
	jstring		result = NULL;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return NULL;

	regErr =
		RegQueryValueEx
			( hKey, NULL, NULL, NULL, NULL, &lBufSize);
	
	if ( regErr != ERROR_SUCCESS && regErr != ERROR_MORE_DATA )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, "(default)" );
		return NULL;
		}

	lpBuffer = malloc( lBufSize + 8 );

	regErr =
		RegQueryValueEx
			( hKey, NULL, NULL, NULL, lpBuffer, &lBufSize);
	
	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, "(default)" );
		return NULL;
		}

	result = asciiToJString( env, lpBuffer );
	if ( result == NULL )
		{
		throwOutOfMemoryError( env, "allocating Java string" );
		}

	return result;
	}

JNIEXPORT jboolean JNICALL
Java_com_ice_jni_registry_RegistryKey_hasDefaultValue
		( JNIEnv *env, jobject rkObject )
	{
	HKEY		hKey;
	LONG		regErr;
	LONG		lBufSize = 0;
	jboolean	result = JNI_FALSE;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return JNI_FALSE;

	regErr =
		RegQueryValueEx
			( hKey, NULL, NULL, NULL, NULL, &lBufSize );
	
	if ( regErr != ERROR_SUCCESS )
		{
		result = JNI_FALSE;
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, "(default)" );
		}
	else if ( lBufSize > 1 )
		{
		result = JNI_TRUE;
		}

	return result;
	}

JNIEXPORT jboolean JNICALL
Java_com_ice_jni_registry_RegistryKey_hasOnlyDefaultValue
		( JNIEnv *env, jobject rkObject )
	{
	HKEY		hKey;
	LONG		regErr;
	DWORD		dwNumValues = 0 ;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return JNI_FALSE;

	regErr =
		RegQueryInfoKey(
			hKey, NULL, NULL, NULL, NULL, NULL, NULL,
			&dwNumValues, NULL, NULL, NULL, NULL );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryInfoKey()", "(default)", NULL );
		return JNI_FALSE;
		}

	if ( dwNumValues != 1 )
		return JNI_FALSE;

	return
		Java_com_ice_jni_registry_RegistryKey_hasDefaultValue
			( env, rkObject );
	}

JNIEXPORT void JNICALL
Java_com_ice_jni_registry_RegistryKey_setValue
		( JNIEnv *env, jobject rkObject,
			jstring valueName, jobject valueObject )
	{
	HKEY		hKey;
	DWORD		dwType;
	char		*asciiName;
	jboolean	result = JNI_FALSE;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return;

	dwType = getRegistryValueType( env, valueObject );
	if ( isThrowing( env ) )
		return;

	if ( dwType < REG_NONE
			|| dwType > REG_RESOURCE_REQUIREMENTS_LIST )
		{
		throwRegErrorException
			( env, ERROR_INVALID_PARAMETER, "RegistryValue.type" );
		return;
		}

	asciiName = jStringToNewAscii( env, valueName );
	if ( asciiName != NULL )
		{
		setKeyValue
			( env, hKey, asciiName, dwType, valueObject );
		free( asciiName );
		}

	return;
	}

JNIEXPORT jint JNICALL
Java_com_ice_jni_registry_RegistryKey_getNumberSubkeys
		( JNIEnv *env, jobject rkObject )
	{
	HKEY		hKey;
	LONG		regErr;
	jint		result = 0;
	DWORD		dwNumSubkeys;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return 0;

	regErr =
		RegQueryInfoKey(
			hKey, NULL, NULL, NULL, &dwNumSubkeys, 
			NULL, NULL, NULL, NULL, NULL, NULL, NULL );

	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegQueryInfoKey()" );
		return 0;
		}

	return (jint) dwNumSubkeys;
	}

JNIEXPORT jint JNICALL
Java_com_ice_jni_registry_RegistryKey_getMaxSubkeyLength
		( JNIEnv *env, jobject rkObject )
	{
	HKEY		hKey;
	LONG		regErr;
	jint		result = 0;
	DWORD		dwMaxSubkeyLength;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return 0;

	regErr = maxSubKeyLength( hKey, &dwMaxSubkeyLength );

	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegQueryInfoKey()" );
		return 0;
		}

	return (jint)dwMaxSubkeyLength;
	}

JNIEXPORT jstring JNICALL
Java_com_ice_jni_registry_RegistryKey_regEnumKey
		( JNIEnv *env, jobject rkObject, jint index )
	{
	HKEY		hKey;
	LONG		regErr;
	LPTSTR		keyBuf;
	FILETIME	lastTime;
	DWORD		dwIndex = (DWORD) index;
	DWORD		dwKeyBufSize, maxLen;
	jstring		result = NULL;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return NULL;

	regErr = maxSubKeyLength( hKey, &maxLen );
	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegQueryInfoKey()" );
		return NULL;
		}

	dwKeyBufSize = maxLen + 2;
	keyBuf = malloc( dwKeyBufSize );
	if ( keyBuf == NULL )
		{
		throwOutOfMemoryError( env, "allocating key name buffer" );
		return NULL;
		}

	regErr =
		RegEnumKeyEx
			( hKey, dwIndex, keyBuf, &dwKeyBufSize,
				NULL, NULL, NULL, &lastTime );

	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegEnumKeyEx()" );
		return NULL;
		}

	result = asciiToJString( env, keyBuf );

	free( keyBuf );

	return result;
	}

JNIEXPORT jstring JNICALL
Java_com_ice_jni_registry_RegistryKey_regEnumValue
		( JNIEnv *env, jobject rkObject, jint index )
	{
	HKEY		hKey;
	jstring		result = NULL;
	LONG		regErr;
	char		*nameBuf;
	DWORD		dwNameBufSize, maxLen;
	DWORD		dwIndex = (DWORD)index;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return NULL;

	regErr = maxValueNameLength( hKey, &maxLen );
	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegQueryInfoKey()" );
		return NULL;
		}

	dwNameBufSize = maxLen + 2;
	nameBuf = malloc( dwNameBufSize );
	if ( nameBuf == NULL )
		{
		throwOutOfMemoryError( env, "allocating value name buffer" );
		return NULL;
		}

	regErr =
		RegEnumValue
			( hKey, dwIndex, nameBuf, &dwNameBufSize,
				NULL, NULL, NULL, NULL );
		
	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegEnumValue()" );
		return NULL;
		}

	result = asciiToJString( env, nameBuf );

	free( nameBuf );

	return result;
	}

JNIEXPORT jint JNICALL
Java_com_ice_jni_registry_RegistryKey_getNumberValues
		( JNIEnv *env, jobject rkObject )
	{
	HKEY		hKey;
	LONG		regErr;
	DWORD		dwNumValues;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return 0;

	regErr =
		RegQueryInfoKey(
			hKey, NULL, NULL, NULL, NULL, NULL,
			NULL, &dwNumValues, NULL, NULL, NULL, NULL );

	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegQueryInfoKey()" );
		return 0;
		}

	return (jint) dwNumValues;
	}

JNIEXPORT jint JNICALL
Java_com_ice_jni_registry_RegistryKey_getMaxValueDataLength
		( JNIEnv *env, jobject rkObject )
	{
	HKEY		hKey;
	LONG		regErr;
	DWORD		dwValueLen;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return 0;

	regErr =
		RegQueryInfoKey(
			hKey, NULL, NULL, NULL, NULL, NULL,
			NULL, NULL, NULL, &dwValueLen, NULL, NULL);

	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegQueryInfoKey()" );
		return 0;
		}

	return (jint) dwValueLen;
	}

JNIEXPORT jint JNICALL
Java_com_ice_jni_registry_RegistryKey_getMaxValueNameLength
		( JNIEnv *env, jobject rkObject )
	{
	HKEY		hKey;
	LONG		regErr;
	DWORD		dwNameLen;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return 0;

	regErr =
		RegQueryInfoKey(
			hKey, NULL, NULL, NULL, NULL, NULL,
			NULL, NULL, &dwNameLen, NULL, NULL, NULL);

	if ( regErr != ERROR_SUCCESS )
		{
		throwRegErrorException( env, regErr, "RegQueryInfoKey()" );
		return 0;
		}

	return (jint) dwNameLen;
	}

JNIEXPORT jint JNICALL
Java_com_ice_jni_registry_RegistryKey_incrDoubleWord
		( JNIEnv *env, jobject rkObject, jstring valueName )
	{
	HKEY		hKey;
	LONG		regErr;
	jint		result = 0;
	char		*lpValueName;
	DWORD		dw = 0;
	DWORD		size = sizeof(dw);
	DWORD		type = REG_DWORD;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return 0;

	lpValueName = jStringToNewAscii( env, valueName );
	if ( lpValueName == NULL )
		{
		throwOutOfMemoryError
			( env, "allocating valueName ascii string" );
		return 0;
		}

	regErr =
		RegQueryValueEx
			( hKey, lpValueName, 0, &type, (LPBYTE)&dw, &size );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, lpValueName );
		free( lpValueName );
		return 0;
		}

	// reinitialize dw if is not REG_DWORD or buffer_overflow
	if ( type != REG_DWORD )
		dw = 0;

	dw++;

	regErr =
		RegSetValueEx
			( hKey, lpValueName, 0,
				REG_DWORD, (LPBYTE)&dw, size );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegSetValueEx()", NULL, lpValueName );
		dw = 0;
		}

	free( lpValueName );

	return (jint) dw;
	}

JNIEXPORT jint JNICALL
Java_com_ice_jni_registry_RegistryKey_decrDoubleWord
		( JNIEnv *env, jobject rkObject, jstring valueName )
	{
	HKEY		hKey;
	LONG		regErr;
	jint		result = 0;
	char		*lpValueName;
	DWORD		dw = 0;
	DWORD		size = sizeof(dw);
	DWORD		type = REG_DWORD;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return 0;

	lpValueName = jStringToNewAscii( env, valueName );
	if ( lpValueName == NULL )
		{
		throwOutOfMemoryError( env, "allocating valueName ascii string" );
		return 0;
		}

	regErr =
		RegQueryValueEx
			( hKey, lpValueName, 0,
				&type, (LPBYTE)&dw, &size );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, lpValueName );
		free( lpValueName );
		return 0;
		}

	if ( type != REG_DWORD )
		dw = 0;

	dw--;

	regErr =
		RegSetValueEx
			( hKey, lpValueName, 0,
				REG_DWORD, (LPBYTE)&dw, size );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegSetValueEx()", NULL, lpValueName );
		dw = 0;
		}

	free( lpValueName );

	return (jint) dw;
	}

JNIEXPORT jobject JNICALL
Java_com_ice_jni_registry_RegistryKey_getValue
		( JNIEnv *env, jobject rkObject, jstring valueName )
	{
	HKEY		hKey;
	LONG		regErr;
	jclass		valClass;
	jarray		binData;
	jarray		stringArray;
	jstring		dataString;
	jmethodID	methodID;
	jobject		newValue;
	jobject		result = NULL;
	char		*lpValueName;
	char		*className;
	char		*methodSignature;
	DWORD		type, dwDataSize;
	DWORD		dwValue;

	hKey = getRegistryKey( env, rkObject );
	if ( isThrowing( env ) )
		return NULL;

	lpValueName = jStringToNewAscii( env, valueName );
	if ( lpValueName == NULL )
		{
		throwOutOfMemoryError( env, "allocating valueName ascii string" );
		return NULL;
		}

	regErr =
		RegQueryValueEx
			( hKey, lpValueName, 0, &type, NULL, &dwDataSize );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, lpValueName );
		free( lpValueName );
		return NULL;
		}
	
	className = "CLASS_WAS_NEVER_SET";
	methodSignature = "METHOD_SIGNATURE_WAS_NOT_SET";
	switch ( type )
		{
		case REG_SZ:
		case REG_EXPAND_SZ:
			className = "com/ice/jni/registry/RegStringValue";
			methodSignature = "(Ljava/lang/String;)V";
			break;

		case REG_MULTI_SZ:
			className = "com/ice/jni/registry/RegMultiStringValue";
			methodSignature = "([Ljava/lang/String;)V";
			break;

		case REG_DWORD:
		case REG_DWORD_BIG_ENDIAN:
			className = "com/ice/jni/registry/RegDWordValue";
			methodSignature = "(I)V";
			break;

		case REG_BINARY:
			className = "com/ice/jni/registry/RegBinaryValue";
			methodSignature = "([B)V";
			break;
		}

	valClass = (*env)->FindClass( env, className );
	
	if ( valClass == NULL )
		{
		throwNoClassDefError( env, className );
		return NULL;
		}

	methodID =
		(*env)->GetMethodID
			( env, valClass, "setData", methodSignature );

	if ( methodID == NULL )
		{
		throwNoSuchMethodError
			( env, className, "setData", methodSignature );
		return NULL;
		}

	newValue =
		newRegistryValue
			( env, rkObject, valueName, className, (jint)type );

	if ( isThrowing( env ) )
		return NULL;

	switch ( type )
		{
		case REG_MULTI_SZ:
			stringArray =
				getMultiStringValueData( env, hKey, lpValueName );

			if ( isThrowing( env ) )
				return NULL;

			(*env)->CallVoidMethod
				( env, newValue, methodID, stringArray );
			break;

		case REG_SZ:
		case REG_EXPAND_SZ:
			dataString =
				getStringValueData( env, hKey, lpValueName );

			if ( isThrowing( env ) )
				return NULL;

			(*env)->CallVoidMethod
				( env, newValue, methodID, dataString );
			break;

		case REG_DWORD:
			// UNDONE - Do we need to deal with BIG_ENDIAN?
			dwValue = getDWordValueData( env, hKey, lpValueName );

			if ( isThrowing( env ) )
				return NULL;

			(*env)->CallVoidMethod
				( env, newValue, methodID, (jint)dwValue );
			break;

		case REG_BINARY:
			binData = getBinaryValueData( env, hKey, lpValueName );

			if ( isThrowing( env ) )
				return NULL;

			(*env)->CallVoidMethod
				( env, newValue, methodID, binData );
			break;
		}

	return newValue;
	}

JNIEXPORT jstring JNICALL
Java_com_ice_jni_registry_RegistryKey_expandEnvStrings
		( JNIEnv *env, jclass rkClass, jstring jExpandStr )
	{
	DWORD		expLen;
	LPTSTR		lpExpandStr;
	LPTSTR		lpExpandResult;
	jstring		result = NULL;
	char		countBuf[8];

	lpExpandStr = jStringToNewAscii( env, jExpandStr );
	if ( lpExpandStr == NULL )
		{
		throwOutOfMemoryError( env, "ascii-ifying expand string" );
		return NULL;
		}

	expLen = ExpandEnvironmentStrings
		( lpExpandStr, countBuf, 1 );
	
	lpExpandResult = malloc( expLen + 8 );
	if ( lpExpandResult == NULL )
		{
		throwOutOfMemoryError( env, "allocating expand result" );
		return NULL;
		}

	expLen = ExpandEnvironmentStrings
		( lpExpandStr, lpExpandResult, expLen );
	
	result = strbufToJString( env, lpExpandResult, (int)expLen );

	free( lpExpandResult );

	if ( result == NULL )
		{
		throwOutOfMemoryError( env, "allocating jstring result" );
		return NULL;
		}

	return result;
	}

HKEY
getRegistryKey( JNIEnv *env, jobject rkObject )
	{
	jclass		rkClass;
	jfieldID	jfid;
	jint		jHKey;

	rkClass = (*env)->GetObjectClass( env, rkObject );
	if ( rkClass == NULL )
		{
		throwNoClassDefError( env, "RegistryKey" );
		return 0;
		}

	jfid = (*env)->GetFieldID( env, rkClass, "hKey", "I" );
	if ( jfid == 0 )
		{
		throwNoSuchFieldError( env, "hKey" );
		return 0;
		}

	jHKey = (*env)->GetIntField( env, rkObject, jfid );
	
	return (HKEY) jHKey;
	}

jstring
getRegistryKeyName( JNIEnv *env, jobject rkObject )
	{
	jfieldID	jfid;
	jstring		jName;
	jclass		rkClass;

	rkClass = (*env)->GetObjectClass( env, rkObject );
	if ( rkClass == NULL )
		{
		throwNoClassDefError( env, "RegistryKey" );
		return NULL;
		}

	jfid =
		(*env)->GetFieldID
			( env, rkClass, "name", "Ljava/lang/String;" );

	if ( jfid == 0 )
		{
		throwNoSuchFieldError( env, "name" );
		return NULL;
		}

	jName = (*env)->GetObjectField( env, rkObject, jfid );
	if ( jName == NULL )
		{
		throwOutOfMemoryError( env, "GetObjectField(name)" );
		return NULL;
		}

	return jName;
	}

jstring
buildFullKeyName( JNIEnv *env, jobject rkObject, char *subKeyName )
	{
	jstring		jName;
	int			dataLen;
	jstring		result = NULL;
	char		*namePtr = NULL;
	char		*newNamePtr = NULL;

	jName = getRegistryKeyName( env, rkObject );
	if ( isThrowing( env ) )
		return NULL;

	namePtr = jStringToNewAscii( env, jName );
	if ( namePtr == NULL )
		{
		throwOutOfMemoryError( env, "jStringToNewAscii(name)" );
		return NULL;
		}

	dataLen = strlen( namePtr ) + strlen( subKeyName ) + 2;

	newNamePtr = malloc( dataLen );
	if ( newNamePtr == NULL )
		{
		throwOutOfMemoryError( env, "malloc(newNamePtr)" );
		}
	else
		{
		sprintf( newNamePtr, "%s\\%s", namePtr, subKeyName );
		result = asciiToJString( env, newNamePtr );
		}

	free( namePtr );
	free( newNamePtr );

	return result;
	}

DWORD
getRegistryValueType( JNIEnv *env, jobject valueObject )
	{
	jfieldID	jfid;
	jint		jType;
	jclass		valClass;

	valClass = (*env)->GetObjectClass( env, valueObject );
	if ( valClass == NULL )
		{
		throwNoClassDefError( env, "RegistryValue" );
		return JNI_FALSE;
		}

	jfid = (*env)->GetFieldID( env, valClass, "type", "I" );
	if ( jfid == 0 )
		{
		throwNoSuchFieldError( env, "type" );
		return JNI_FALSE;
		}

	jType = (*env)->GetIntField( env, valueObject, jfid );
	
	return (int) jType;
	}


void
setKeyValue(
		JNIEnv *env, HKEY hKey, char *valueName,
		DWORD dwType, jobject valObject )
	{
	int			i;
	LONG		regErr;
	DWORD		dataLen;
	DWORD		dwValue;
	LPBYTE		ptr;
	LPBYTE		dataPtr;
	jint		jIntVal, numStr, sLen;
	jboolean	isCopied;
	jclass		valClass;
	jstring		jStr;
	jfieldID	jfid;
	jarray		jBinArray, jStrArray;
	jarray		freeArray = NULL;
	LPBYTE		freePtr = NULL;
	char		emptyBuf[4];

	valClass = (*env)->GetObjectClass( env, valObject );
	if ( valClass == NULL )
		{
		throwNoClassDefError
			( env, "setKeyValue: getting RegistryValue class" );
		return;
		}

	switch ( dwType )
		{
		case REG_SZ:
		case REG_EXPAND_SZ:
			jfid = (*env)->GetFieldID
					( env, valClass, "data", "Ljava/lang/String;" );

			if ( jfid == 0 )
				{
				throwNoSuchFieldError( env, "setKeyValue: String data" );
				return;
				}

			jStr = (*env)->GetObjectField( env, valObject, jfid );
			if ( jStr == NULL )
				{
				throwOutOfMemoryError( env, "GetObjectField(data)" );
				return;
				}

			dataPtr = freePtr = jStringToNewAscii( env, jStr );
			if ( dataPtr == NULL )
				{
				throwOutOfMemoryError
					( env, "setKeyValue: jStringToNewAscii" );
				return;
				}
			dataLen = strlen( dataPtr ) + 1;
			break;

		case REG_MULTI_SZ:
			jfid = (*env)->GetFieldID
					( env, valClass, "data", "[Ljava/lang/String;" );

			if ( jfid == 0 )
				{
				throwNoSuchFieldError( env, "setKeyValue: String[] data" );
				return;
				}

			jStrArray = (jarray)
				(*env)->GetObjectField( env, valObject, jfid );

			if ( jStrArray == NULL )
				{
				throwOutOfMemoryError( env, "GetObjectField(data)" );
				return;
				}

			numStr = (*env)->GetArrayLength( env, jStrArray );
			for ( dataLen = 0, i = 0 ; i < numStr ; ++i )
				{
				jStr = (jstring)
					(*env)->GetObjectArrayElement
						( env, jStrArray, i );

				sLen = (*env)->GetStringLength( env, jStr );
				dataLen += sLen + 1;
				}

			dataLen++; // remember double null to terminate array...

			dataPtr = freePtr = malloc( dataLen + 32 );
			if ( dataPtr == NULL )
				{
				throwOutOfMemoryError( env, "setKeyValue: dataPtr" );
				return;
				}

			for ( ptr = dataPtr, i = 0 ; i < numStr ; ++i )
				{
				jStr = (jstring)
					(*env)->GetObjectArrayElement
						( env, jStrArray, i );

				if ( jStr == NULL )
					{
					sLen = 0;
					}
				else
					{
					sLen = (*env)->GetStringLength( env, jStr );
					jStringToAscii( env, jStr, ptr, (sLen + 1) );
					}

				ptr += sLen;
				*ptr++ = '\0';
				}

			*ptr++ = '\0'; // remember double null to terminate...
			break;

		case REG_DWORD:
		case REG_DWORD_BIG_ENDIAN:
			jfid = (*env)->GetFieldID( env, valClass, "data", "I" );
			if ( jfid == 0 )
				{
				throwNoSuchFieldError( env, "setKeyValue: int data" );
				return;
				}

			jIntVal = (*env)->GetIntField( env, valObject, jfid );

			dwValue = (DWORD)jIntVal;
			dataPtr = (LPBYTE) &dwValue;
			dataLen = sizeof( dwValue );
			break;

		case REG_BINARY:
			jfid = (*env)->GetFieldID( env, valClass, "data", "[B" );
			if ( jfid == 0 )
				{
				throwNoSuchFieldError( env, "setKeyValue: byte[] data" );
				return;
				}

			jBinArray = (jarray)
				(*env)->GetObjectField( env, valObject, jfid );

			if ( jBinArray == NULL )
				{
				dataLen = 0;
				dataPtr = emptyBuf;
				}
			else
				{
				freeArray = jBinArray;
				dataLen = (*env)->GetArrayLength( env, jBinArray );
				dataPtr = (LPBYTE)
					(*env)->GetByteArrayElements
						( env, jBinArray, &isCopied );

				if ( dataPtr == NULL )
					{
					throwOutOfMemoryError
						( env, "setKeyValue: GetByteArrayElements" );
					return;
					}
				}
			break;
		}

	regErr =
		RegSetValueEx
			( hKey, valueName, 0, dwType, dataPtr, dataLen );

	if ( freePtr != NULL )
		free( freePtr );

	if ( freeArray != NULL )
		(*env)->ReleaseByteArrayElements
			( env, freeArray, dataPtr, JNI_ABORT );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegSetValueEx()", NULL, NULL );
		}
	}

BOOL
RegIsHKeyRemote( HKEY hKey ) 
	{
	DWORD	dw = (DWORD)hKey;

	if ( (hKey >= HKEY_CLASSES_ROOT)
			&& (hKey <= HKEY_DYN_DATA) )
		{
		return FALSE; // local
		}

	return ( ( (~dw) & 1 ) == 0 );
	}

LONG
maxSubKeyLength( HKEY hKey, DWORD *pdwSubkeyLen )
	{
	LONG	regErr;

	regErr =
		RegQueryInfoKey(
			hKey, NULL, NULL, NULL, NULL, 
			pdwSubkeyLen, NULL, NULL, NULL,
			NULL, NULL, NULL );

	// NOTE
	// This algorithm is due to a bug related to remote
	// registries in Win95. This fix comes from pp 129-130
	// in the "Inside The Windows 95 Registry" book from
	// O'Reilly & Assocvates, First Edition.
	//
	if ( regErr == ERROR_SUCCESS && RegIsHKeyRemote( hKey ) )
		{
		*pdwSubkeyLen = (*pdwSubkeyLen * 2) + 2;
		}

	return regErr;
	}

LONG
maxValueNameLength( HKEY hKey, DWORD *pdwNameLen )
	{
	LONG	regErr;

	regErr =
		RegQueryInfoKey(
			hKey, NULL, NULL, NULL, NULL, NULL,
			NULL, NULL, pdwNameLen, NULL, NULL, NULL );

	// REVIEW Where does this algorithm come from?
	if ( regErr == ERROR_SUCCESS && RegIsHKeyRemote( hKey ) )
		{
		*pdwNameLen = (*pdwNameLen * 2) + 2;
		}

	return regErr;
	}

DWORD
getDWordValueData( JNIEnv *env, HKEY hKey, char *valueName )
	{
	LONG		regErr;
	DWORD		dwType;
	DWORD		dwValue = 0;
	DWORD		dwBufSize = sizeof(dwValue);

	regErr =
		RegQueryValueEx
			( hKey, valueName, NULL, &dwType, (LPBYTE)&dwValue, &dwBufSize );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, valueName );
		return 0;
		}
	
	if ( dwType != REG_DWORD || dwType == REG_DWORD_BIG_ENDIAN )
		{
		throwRegErrorException
			( env, ERROR_INVALID_PARAMETER, "type is not REG_DWORD" );
		return 0;
		}

	return dwValue;
	}

jarray
getMultiStringValueData( JNIEnv *env, HKEY hKey, char *valueName )
	{
	jarray		result;
	int			i, start, sLen, count, cnt;
	LONG		regErr;
	char		*strData;
	char		*className;
	jstring		jStr;
	jclass		strClass;
	DWORD		dwType;
	DWORD		dwBufSize = 0;

	regErr =
		RegQueryValueEx
			( hKey, valueName, NULL, &dwType, NULL, &dwBufSize );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, valueName );
		return NULL;
		}
	
	if ( dwType != REG_MULTI_SZ )
		{
		throwRegErrorException
			( env, ERROR_INVALID_PARAMETER, "type is not REG_MULTI_SZ" );
		return NULL;
		}

	strData = malloc( dwBufSize + 8 );
	if ( strData == NULL )
		{
		throwOutOfMemoryError
			( env, "setKeyValue: getMultiStringValueData: strData" );
		return NULL;
		}

	regErr =
		RegQueryValueEx
			( hKey, valueName, NULL, &dwType, strData, &dwBufSize );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, valueName );
		free( strData );
		return NULL;
		}

	for ( count = 0, i = 0 ; i < (int)dwBufSize ; ++i )
		{
		if ( strData[i] == '\0' )
			{
			++count;
			if ( strData[ i + 1 ] == '\0' )
				break;
			}
		}

	className = "java/lang/String";
	strClass = (*env)->FindClass( env, className );
	if ( strClass == NULL )
		{
		throwNoClassDefError( env, className );
		return NULL;
		}

	result =
		(*env)->NewObjectArray
			( env, (jsize)count, strClass, NULL );

	if ( result == NULL )
		{
		throwOutOfMemoryError
			( env, "getMultiStringValueData: NewObjectArray" );
		return NULL;
		}

	for ( cnt = 0, start = 0, i = 0
			; i < (int)dwBufSize && cnt < count ; ++i )
		{
		if ( strData[i] == '\0' )
			{
			sLen = (i - start);

			jStr = strbufToJString( env, &strData[start], sLen );
			if ( jStr == NULL )
				{
				throwOutOfMemoryError
					( env, "getMultiStringValueData: strbufToJString" );
				return NULL;
				}

			(*env)->SetObjectArrayElement
				( env, result, (jsize)cnt++, jStr );

			start = i + 1;
			if ( strData[ start ] == '\0' )
				break;
			}
		}

	return result;
	}

jstring
getStringValueData( JNIEnv *env, HKEY hKey, char *valueName )
	{
	DWORD		i;
	LONG		regErr;
	char		*strData;
	jchar		*uniBuf;
	jstring		result = NULL;
	DWORD		dwType;
	DWORD		dwBufSize = 0;

	regErr =
		RegQueryValueEx
			( hKey, valueName, NULL, &dwType, NULL, &dwBufSize );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, valueName );
		return NULL;
		}
	
	if ( dwType != REG_SZ || dwType == REG_EXPAND_SZ )
		{
		throwRegErrorException
			( env, ERROR_INVALID_PARAMETER, "type is not REG_SZ" );
		return NULL;
		}

	strData = malloc( dwBufSize + 8 );
	if ( strData == NULL )
		{
		throwOutOfMemoryError
			( env, "getStringValueData: strData" );
		return NULL;
		}

	regErr =
		RegQueryValueEx
			( hKey, valueName, NULL, &dwType, strData, &dwBufSize );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, valueName );
		free( strData );
		return NULL;
		}

	uniBuf = (jchar *)
		malloc( sizeof(jchar) * (dwBufSize + 2) );

	if ( uniBuf == NULL )
		{
		throwOutOfMemoryError
			( env, "getStringValueData: uniBuf" );
		return NULL;
		}

	--dwBufSize;
	for ( i = 0 ; i < dwBufSize ; ++i )
		uniBuf[i] = (jchar)  strData[i];

	result =
		(*env)->NewString
			( env, uniBuf, (jsize)dwBufSize );

	free( uniBuf );

	return result;
	}

jarray
getBinaryValueData( JNIEnv *env, HKEY hKey, char *valueName )
	{
	LONG		regErr;
	char		*regData;
	jstring		result = NULL;
	DWORD		dwType;
	DWORD		dwBufSize = 0;
	jarray		binBytes = NULL;

	regErr =
		RegQueryValueEx
			( hKey, valueName, NULL, &dwType, NULL, &dwBufSize );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, valueName );
		return NULL;
		}
	
	if ( dwType != REG_BINARY )
		{
		throwRegErrorException
			( env, ERROR_INVALID_PARAMETER, "type is not REG_BINARY" );
		return NULL;
		}

	regData = malloc( dwBufSize + 8 );
	if ( regData == NULL )
		{
		throwOutOfMemoryError
			( env, "getBinaryValueData: regData" );
		return NULL;
		}

	regErr =
		RegQueryValueEx
			( hKey, valueName, NULL, &dwType, regData, &dwBufSize );

	if ( regErr != ERROR_SUCCESS )
		{
		throwSpecificRegError
			( env, regErr, "RegQueryValueEx()", NULL, valueName );
		free( regData );
		return NULL;
		}

	binBytes = (*env)->NewByteArray( env, (jsize)dwBufSize );
	if ( regData == NULL )
		{
		throwOutOfMemoryError
			( env, "getBinaryValueData: binBytes" );
		return NULL;
		}

	(*env)->SetByteArrayRegion
		( env, binBytes, 0, (jsize)dwBufSize, regData );

	return binBytes;
	}

jstring
asciiToJString( JNIEnv *env, char *asciiStr )
	{
	return strbufToJString( env, asciiStr, strlen(asciiStr) );
	}

jstring
strbufToJString( JNIEnv *env, char *buf, int len )
	{
	int			i;
	jchar		*uniBuf;
	jstring		result = NULL;

	uniBuf = (jchar *)
		malloc( sizeof(jchar) * (len + 1) );

	if ( uniBuf != NULL )
		{
		for ( i = 0 ; i < len ; ++i )
			uniBuf[i] = (jchar)  buf[i];

		result = (*env)->NewString( env, uniBuf, (jsize)len );

		free( uniBuf );
		}

	return result;
	}

char *
jStringToNewAscii( JNIEnv *env, jstring jStr )
	{
	int			i;
	int			utfLen;
	jboolean	isCopy;
	char		*asciiBuf;
	const char	*utfBuf;

	utfLen = (*env)->GetStringUTFLength( env, jStr );
	utfBuf = (*env)->GetStringUTFChars( env, jStr, &isCopy );

	asciiBuf = malloc( utfLen + 2 );
	if ( asciiBuf != NULL )
		{
		for ( i = 0 ; i < utfLen ; ++i )
			asciiBuf[i] = utfBuf[i];

		asciiBuf[i] = '\0';

		(*env)->ReleaseStringUTFChars( env, jStr, utfBuf );
		}

	return asciiBuf;
	}

void
jStringToAscii
		( JNIEnv *env, jstring jStr, char *asciiBuf, int asciiLen )
	{
	int			i;
	int			utfLen;
	jboolean	isCopy;
	const char	*utfBuf;

	utfLen = (*env)->GetStringUTFLength( env, jStr );
	utfBuf = (*env)->GetStringUTFChars( env, jStr, &isCopy );

	if ( utfBuf != NULL )
		{
		for ( i = 0 ; i < utfLen && i < (asciiLen - 1) ; ++i )
			asciiBuf[i] = utfBuf[i];

		asciiBuf[i] = '\0';

		(*env)->ReleaseStringUTFChars( env, jStr, utfBuf );
		}
	}

jobject
newRegistryKey( JNIEnv *env, HKEY hKey, jstring keyName, BOOL created )
	{
	jclass		rkClass;
	jobject		newKey;
	jmethodID	methodID;
	jboolean	cFlag = (jboolean) created;

	char		*methodName = "<init>";
	char		*methodSignature = "(ILjava/lang/String;Z)V";
	char		*className = "com/ice/jni/registry/RegistryKey";

	rkClass = (*env)->FindClass( env, className );
	if ( rkClass == NULL )
		{
		throwNoClassDefError( env, (char *)className );
		return NULL;
		}

	methodID =
		(*env)->GetMethodID
			( env, rkClass, methodName, methodSignature );

	if ( methodID == NULL )
		{
		throwNoSuchMethodError
			( env, className, methodName, methodSignature );
		return NULL;
		}

	newKey =
		(*env)->NewObject
			( env, rkClass, methodID,
				(jint)hKey, keyName, (jboolean)cFlag );

	return newKey;
	}

jobject
newRegistryValue(
		JNIEnv *env, jobject rkObject,
		jstring valueName, char *className, int type )
	{
	jclass		rkClass;
	jobject		newValue;
	jmethodID	methodID;

	char		*methodName = "<init>";
	char		*methodSignature =
		"(Lcom/ice/jni/registry/RegistryKey;Ljava/lang/String;I)V";

	rkClass = (*env)->FindClass( env, className );
	if ( rkClass == NULL )
		{
		throwNoClassDefError( env, className );
		return NULL;
		}

	methodID =
		(*env)->GetMethodID
			( env, rkClass, methodName, methodSignature );

	if ( methodID == NULL )
		{
		throwNoSuchMethodError
			( env, className, methodName, methodSignature );
		return NULL;
		}

	newValue =
		(*env)->NewObject
			( env, rkClass, methodID, rkObject, valueName, (jint)type );

	return newValue;
	}

BOOL
isThrowing( JNIEnv *env )
	{
	return ( (*env)->ExceptionOccurred(env) != NULL );
	}

jint
throwNoSuchKeyException( JNIEnv *env, char *message )
	{
	jclass		exClass;
	char		*className =
		"com/ice/jni/registry/NoSuchKeyException";

	exClass = (*env)->FindClass( env, className );
	if ( exClass == NULL )
		{
		return throwNoClassDefError( env, className );
		}

	return (*env)->ThrowNew( env, exClass, message );
	}

jint
throwNoSuchValueException( JNIEnv *env, char *message )
	{
	jclass		exClass;
	char		*className =
		"com/ice/jni/registry/NoSuchValueException";

	exClass = (*env)->FindClass( env, className );
	if ( exClass == NULL )
		{
		return throwNoClassDefError( env, className );
		}

	return (*env)->ThrowNew( env, exClass, message );
	}

void
throwSpecificRegError
		( JNIEnv *env, LONG regErr, char *message,
			char *keyName, char *valueName )
	{
	char	msgBuf[512];

	if ( regErr != ERROR_FILE_NOT_FOUND
			|| ( keyName == NULL && valueName == NULL ) )
		{
		throwRegErrorException( env, regErr, message );
		}
	else if ( keyName != NULL )
		{
		sprintf( msgBuf, "%.200s, key='%.200s'", message, keyName );
		throwNoSuchKeyException( env, msgBuf );
		}
	else
		{
		sprintf( msgBuf, "%.200s, value='%.200s'", message, valueName );
		throwNoSuchValueException( env, msgBuf );
		}
	}

jint
throwRegErrorException( JNIEnv *env, LONG regErr, char *message )
	{
	jclass		exClass;
	jmethodID	methodID;
	jobject		exObject;
	jstring		jMsgString;
	char		strBuf[512];
	char		*regDescription = "";
	char		*className = "com/ice/jni/registry/RegistryException";
	char		*methodSignature = "(Ljava/lang/String;I)V";

	exClass = (*env)->FindClass( env, className );
	if ( exClass == NULL )
		{
		return throwNoClassDefError( env, className );
		}

	methodID =
		(*env)->GetMethodID
			( env, exClass, "<init>", methodSignature );

	if ( methodID == NULL )
		{
		return throwNoSuchMethodError
			( env, className, "<init>", methodSignature );
		}

	jMsgString = strbufToJString( env, message, strlen(message) );
	if ( jMsgString == NULL )
		{
		return throwOutOfMemoryError
			( env, "throwRegErrorException: creating message string" );
		}

	exObject =
		(*env)->NewObject
			( env, exClass, methodID, jMsgString, (jint)regErr );

	switch ( regErr )
		{
		case ERROR_FILE_NOT_FOUND:
			regDescription = "not found";
			break;
		case ERROR_ACCESS_DENIED:
			regDescription = "access denied";
			break;
		case ERROR_INVALID_HANDLE:
			regDescription = "invalid handle";
			break;
		case ERROR_INVALID_PARAMETER:
			regDescription = "invalid parameter";
			break;
		case ERROR_CALL_NOT_IMPLEMENTED:
			regDescription = "call not implemented";
			break;
		case ERROR_INSUFFICIENT_BUFFER:
			regDescription = "insufficient buffer";
			break;
		case ERROR_LOCK_FAILED:
			regDescription = "lock failed";
			break;
		case ERROR_MORE_DATA:
			regDescription = "more data";
			break;
		case ERROR_NO_MORE_ITEMS:
			regDescription = "no more items";
			break;
		case ERROR_BADDB:
			regDescription = "bad DB";
			break;
		case ERROR_BADKEY:
			regDescription = "bad key";
			break;
		case ERROR_CANTOPEN:
			regDescription = "can not open";
			break;
		case ERROR_CANTREAD:
			regDescription = "can not read";
			break;
		case ERROR_CANTWRITE:
			regDescription = "can not write";
			break;
		case ERROR_REGISTRY_RECOVERED:
			regDescription = "registry recovered";
			break;
		case ERROR_REGISTRY_CORRUPT:
			regDescription = "registry corrupt";
			break;
		case ERROR_REGISTRY_IO_FAILED:
			regDescription = "registry IO failed";
			break;
		case ERROR_NOT_REGISTRY_FILE:
			regDescription = "not a registry file";
			break;
		case ERROR_KEY_DELETED:
			regDescription = "key has been deleted";
			break;
		}

	sprintf( strBuf,
		"Registry API Error %d, '%.128s' - '%.256s'",
			regErr, regDescription, message );

	return (*env)->ThrowNew( env, exClass, strBuf );
	}

jint
throwNoClassDefError( JNIEnv *env, char *message )
	{
	jclass		exClass;
	char		*className = "java/lang/NoClassDefFoundError";

	exClass = (*env)->FindClass( env, className );
	if ( exClass == NULL )
		{
		return throwNoClassDefError( env, className );
		}

	return (*env)->ThrowNew( env, exClass, message );
	}

jint
throwNoSuchMethodError(
		JNIEnv *env, char *className, char *methodName, char *signature )
	{
	jclass		exClass;
	char		*exClassName = "java/lang/NoSuchMethodError";
	LPTSTR		msgBuf;

	exClass = (*env)->FindClass( env, exClassName );
	if ( exClass == NULL )
		{
		return throwNoClassDefError( env, exClassName );
		}
	
	msgBuf = malloc( strlen(className) + strlen(methodName)
						+ strlen(signature) + 8 );
	if ( msgBuf == NULL )
		{
		return throwOutOfMemoryError
			( env, "throwNoSuchMethodError: allocatinf msgBuf" );
		}

	strcpy( msgBuf, className );
	strcat( msgBuf, "." );
	strcat( msgBuf, methodName );
	strcat( msgBuf, "." );
	strcat( msgBuf, signature );

	return (*env)->ThrowNew( env, exClass, msgBuf );
	}

jint
throwNoSuchFieldError( JNIEnv *env, char *message )
	{
	jclass		exClass;
	char		*className = "java/lang/NoSuchFieldError";

	exClass = (*env)->FindClass( env, className );
	if ( exClass == NULL )
		{
		return throwNoClassDefError( env, className );
		}

	return (*env)->ThrowNew( env, exClass, message );
	}

jint
throwOutOfMemoryError( JNIEnv *env, char *message )
	{
	jclass		exClass;
	char		*className = "java/lang/OutOfMemoryError";

	exClass = (*env)->FindClass( env, className );
	if ( exClass == NULL )
		{
		return throwNoClassDefError( env, className );
		}

	return (*env)->ThrowNew( env, exClass, message );
	}



