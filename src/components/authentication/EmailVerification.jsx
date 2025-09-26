import React, { useContext, useEffect, useState } from 'react'
import { useAuth } from './AuthenticationProvider'
import { InputOTP, InputOTPGroup, InputOTPSlot } from '@/components/ui/input-otp'
import { MailCheck, AlertCircle } from 'lucide-react'
import { verifyEmail, resendVerificationEmail } from '../../services/auth-service'
import { useAlert } from '../../SharedLayout'
import { useNavigate } from 'react-router'

const EmailVerification = () => {
  const { auth, setAuth } = useAuth();
  const [otp, setOtp] = useState('');
  const {addAlert} = useAlert();
  const navigate = useNavigate();

  useEffect(() => {
    if (otp.length === 6) {
      verifyEmail(otp).then(response => {
        console.log(response.data);
        if(response.data.isSuccess){
          addAlert(true, response.data.message);
          setAuth({...auth, isEmailVerified: true});
          navigate("/evenements");
        }else{
          addAlert(false, "Code est invalide ou expiré.");
        }
      }).catch((error) => {
        setOtp('');
        addAlert(false, "Code est invalide ou expiré.");
      })
    }
  }, [otp]);

  const resendVerification = () => {
    resendVerificationEmail().then(response => {
        if(response.data.isSuccess){
          addAlert(true, response.data.message);
        }else {
          addAlert(false, response.data.message);
        }
        setOtp('');
      }).catch((error) => {
        setOtp('');
        addAlert(false, error.response?.data?.message || "Une erreur est survenue. Veuillez réessayer plus tard.");
      })
  }

  return (
    <section className='flex flex-col items-center my-30'>
      <div className='w-[45vw] max-md:min-w-[90vw]'>
        <h1 className='text-3xl font-bold'>Bonjour {auth.fullName},</h1>

        <div className="mb-8 mt-8">
          <div className="bg-[#E2F2FF] rounded-lg p-4 mb-6 py-5 pl-8">
            <MailCheck className="w-6 h-6 text-[#0C5F95] mb-2" />
            <p className="text-sm text-[#0C5F95]">
              Un email de vérification a été envoyé à :
            </p>
            <p className="font-semibold text-[#0C5F95] mt-1">
              {auth.email}
            </p>
          </div>
        </div>

        <p>Saisissez le code de vérification reçu :</p>

        <InputOTP containerClassName='mt-3' maxLength={6} value={otp} onChange={(o) => {setOtp(o.toUpperCase())}}>
          <InputOTPGroup>
            <InputOTPSlot index={0} className='w-15 h-15 text-2xl'/>
            <InputOTPSlot index={1} className='w-15 h-15 text-2xl'/>
            <InputOTPSlot index={2} className='w-15 h-15 text-2xl'/>
            <InputOTPSlot index={3} className='w-15 h-15 text-2xl'/>
            <InputOTPSlot index={4} className='w-15 h-15 text-2xl'/>
            <InputOTPSlot index={5} className='w-15 h-15 text-2xl'/>
          </InputOTPGroup>
        </InputOTP>

        <p onClick={resendVerification} className='hover:underline text-sm cursor-pointer mt-1 text-[#053A5F]'>Renvoyer un code?</p>

        <div className="mt-8 p-4 bg-gray-50 rounded-lg">
          <div className="flex items-start space-x-3">
            <AlertCircle className="w-5 h-5 text-amber-500 mt-0.5 flex-shrink-0" />
            <div className="text-sm text-gray-600">
              <p className="font-medium text-gray-700 mb-1">N.B :</p>
              <p>Le lien de vérification expire dans 1 heure.</p>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

export default EmailVerification