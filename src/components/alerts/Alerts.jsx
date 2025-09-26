import React, { useContext } from 'react'
import { AlertCircleIcon } from 'lucide-react'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'


const Alerts = ({alerts}) => {
  return (
    <div className='fixed top-21 right-5 z-20 space-y-4 w-80 opacity-80'>
      {alerts.map((alert) => {
        return (
          <Alert key={alert.id} className={alert.success 
            ? 'bg-green-100 border-green-200 max-w-100'
            : 'bg-red-100 border-red-200 max-w-100'}>
            <AlertCircleIcon color={alert.success? 'green': 'red'}/>
            <AlertDescription className='text-black'>{alert.description}</AlertDescription>
        </Alert>
        )
      })}
    </div>
  )
}

export default Alerts